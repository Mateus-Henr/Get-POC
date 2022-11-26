package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.Professor_has_subjectDB;
import com.ufv.project.db.SubjectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserController
{
    @FXML
    private FlowPane mainPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label registrationLabel;

    @FXML
    private TextField registrationTextField;

    @FXML
    private TextField POCIDTextField;

    @FXML
    private Label professorSubjectsLabel;

    @FXML
    private MenuButton professorSubjects;

    @FXML
    private Button updateUserButton;

    @FXML
    private ProgressIndicator progressIndicator;

    private final DataModel dataModel;

    public UpdateUserController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {
        usernameTextField.setText(dataModel.getUsername());
        nameTextField.setText(dataModel.getName());
        UserTypesEnum userType = dataModel.getUserType();

        if (userType == UserTypesEnum.ADMIN)
        {
            professorSubjectsLabel.setManaged(false);
            professorSubjects.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjects.setVisible(false);

            registrationLabel.setManaged(false);
            registrationTextField.setManaged(false);
            registrationLabel.setVisible(false);
            registrationTextField.setVisible(false);

            emailLabel.setManaged(false);
            emailTextField.setManaged(false);
            emailLabel.setVisible(false);
            emailTextField.setVisible(false);
        }
        else if (userType == UserTypesEnum.STUDENT)
        {
            professorSubjectsLabel.setManaged(false);
            professorSubjects.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjects.setVisible(false);

            registrationLabel.setManaged(true);
            registrationTextField.setManaged(true);
            registrationLabel.setVisible(true);
            registrationTextField.setVisible(true);

            emailLabel.setManaged(true);
            emailTextField.setManaged(true);
            emailLabel.setVisible(true);
            emailTextField.setVisible(true);
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            registrationLabel.setManaged(false);
            registrationTextField.setManaged(false);
            registrationLabel.setVisible(false);
            registrationTextField.setVisible(false);

            emailLabel.setManaged(true);
            emailTextField.setManaged(true);
            emailLabel.setVisible(true);
            emailTextField.setVisible(true);

            professorSubjectsLabel.setManaged(true);
            professorSubjects.setManaged(true);
            professorSubjectsLabel.setVisible(true);
            professorSubjects.setVisible(true);
        }

        new Thread(new Task<Void>()
        {
            @Override
            protected Void call() throws SQLException
            {
                try (ConnectDB connectDB = new ConnectDB())
                {
                    if (userType == UserTypesEnum.STUDENT)
                    {
                        registrationTextField.setText(dataModel.getRegistration());
                        emailTextField.setText(dataModel.getEmail());
                        POCIDTextField.setText(String.valueOf(((Student) new UserDB(connectDB.getConnection())
                                .queryUserByID(dataModel.getUsername())).getPOCID()));
                    }
                    else if (userType == UserTypesEnum.PROFESSOR)
                    {
                        emailTextField.setText(dataModel.getEmail());

                        List<Subject> markedSubjects = new Professor_has_subjectDB(connectDB.getConnection()).querySubjectsByProfessor(dataModel.getUsername());

                        professorSubjects.getItems().setAll(initializeCheckMenuItemsFromList(new SubjectDB(connectDB.getConnection()).querySubjects()));

                        for (MenuItem menuItem : professorSubjects.getItems())
                        {
                            markedSubjects.stream().map(Subject::getId).forEach(integer -> ((CheckMenuItem) menuItem)
                                    .setSelected(integer == Integer.parseInt(menuItem.getId())));
                        }
                    }

                    return null;
                }
            }
        }).start();

    }

    @FXML
    public void onUpdateButtonClicked()
    {
        final Task<User> task = new Task<>()
        {
            @Override
            protected User call() throws Exception
            {
                User user = null;

                if (dataModel.getUserType() == UserTypesEnum.STUDENT)
                {
                    String POCIDText = POCIDTextField.getText().trim();
                    int POCID = 0;

                    if (!POCIDText.isEmpty())
                    {
                        POCID = Integer.parseInt(POCIDText);
                    }

                    user = new Student(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            registrationTextField.getText().trim(),
                            POCID,
                            emailTextField.getText().trim());
                }
                else if (dataModel.getUserType() == UserTypesEnum.PROFESSOR)
                {
                    List<Integer> subjectIDList = professorSubjects
                            .getItems()
                            .stream()
                            .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                            .map(menuItem -> Integer.parseInt(menuItem.getId()))
                            .toList();

                    try (ConnectDB connectDB = new ConnectDB())
                    {
                        SubjectDB subjectDB = new SubjectDB(connectDB.getConnection());
                        List<Subject> subjectList = new ArrayList<>();

                        for (int id : subjectIDList)
                        {
                            subjectList.add(subjectDB.querySubjectByID(id));
                        }

                        user = new Professor(usernameTextField.getText().trim(),
                                nameTextField.getText().trim(),
                                passwordField.getText(),
                                emailTextField.getText().trim(),
                                subjectList);
                    }
                }
                else if (dataModel.getUserType() == UserTypesEnum.ADMIN)
                {
                    user = new Administrator(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText());
                }

                if (user == null)
                {
                    throw new IllegalAccessException("ERROR: No role was assigned.");
                }

                try (ConnectDB connectDB = new ConnectDB())
                {
                    return new UserDB(connectDB.getConnection()).updateUser(user);
                }
            }
        };

        task.setOnSucceeded(workerStateEvent -> Main.closeCurrentStage(mainPane));

        task.setOnFailed(workerStateEvent -> task.getException().printStackTrace());

        new Thread(task).start();
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        updateUserButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
    }

    @FXML
    public void onSelectSubject()
    {
        professorSubjects.setText(professorSubjects.getItems().stream()
                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                .count() + " subject(s) selected");
    }

    public List<MenuItem> initializeCheckMenuItemsFromList(List<Subject> subjectList)
    {
        List<MenuItem> items = new ArrayList<>();

        for (Subject subject : subjectList)
        {
            CheckMenuItem menuItem = new CheckMenuItem();

            menuItem.setId(String.valueOf(subject.getId()));
            menuItem.setText(subject.getName() + " - " + subject.getDescription());

            items.add(menuItem);
        }

        return items;
    }

}
