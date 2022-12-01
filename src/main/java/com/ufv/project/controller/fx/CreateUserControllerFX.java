package com.ufv.project.controller.fx;

import com.ufv.project.controller.java.CreateUserController;
import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
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

import java.util.regex.*;

public class CreateUserControllerFX
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
    private Label POCIDLabel;

    @FXML
    private TextField POCIDTextField;

    @FXML
    private Label professorSubjectsLabel;

    @FXML
    private MenuButton professorSubjects;

    @FXML
    private RadioButton studentRadioButton;

    @FXML
    private RadioButton professorRadioButton;

    @FXML
    private RadioButton adminRadioButton;

    @FXML
    private Button createUserButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize()
    {
        // Disables button until every field has been populated.
        createUserButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                        usernameTextField.getText().trim().isEmpty(),
                                usernameTextField.textProperty())
                        .or(Bindings.createBooleanBinding(() ->
                                                nameTextField.getText().trim().isEmpty(),
                                        nameTextField.textProperty())
                                .or(Bindings.createBooleanBinding(() ->
                                                        passwordField.getText().trim().isEmpty(),
                                                passwordField.textProperty())
                                        .or(Bindings.createBooleanBinding(() ->
                                                        confirmPasswordField.getText().trim().isEmpty(),
                                                confirmPasswordField.textProperty()))
                                )));

        // Set up page according to the selected one.
        onRadioButtonChanged();
    }



    @FXML
    public void onRadioButtonChanged()
    {
        if (studentRadioButton.isSelected())
        {
            POCIDLabel.setManaged(true);
            POCIDLabel.setManaged(true);
            POCIDTextField.setVisible(true);
            POCIDTextField.setVisible(true);

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
        else if (professorRadioButton.isSelected())
        {
            POCIDLabel.setManaged(false);
            POCIDLabel.setManaged(false);
            POCIDTextField.setVisible(false);
            POCIDTextField.setVisible(false);

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

            // Fetches data from db
            final Task<List<MenuItem>> task = new Task<>()
            {
                @Override
                protected List<MenuItem> call() throws SQLException
                {
                    try (ConnectDB connectDB = new ConnectDB())
                    {
                        return initializeCheckMenuItemsFromList(new SubjectDB(connectDB.getConnection()).querySubjects());
                    }
                }
            };

            task.setOnSucceeded(workerStateEvent -> professorSubjects.getItems().setAll(task.getValue()));
            task.setOnFailed(workerStateEvent -> task.getException().printStackTrace());

            new Thread(task).start();
        }
        else if (adminRadioButton.isSelected())
        {
            POCIDLabel.setManaged(false);
            POCIDLabel.setManaged(false);
            POCIDTextField.setVisible(false);
            POCIDTextField.setVisible(false);

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
    }

    @FXML
    public void onSelectSubject()
    {
        professorSubjects.setText(professorSubjects.getItems().stream()
                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                .count() + " subject(s) selected");
    }

    @FXML
    public void onCreateButtonPressed()
    {
       if (!CreateUserController.arePasswordsEqual(passwordField.getText(), confirmPasswordField.getText()) &&
               !CreateUserController.checkEmail(emailTextField.getText()) &&
               !CreateUserController.checkRegistration(registrationTextField.getText()))
        {
            // Display error style on password input boxes.
            return;
        }


        final Task<String> task = new Task<>()
        {
            @Override
            protected String call() throws Exception
            {
                User user = null;

                if (studentRadioButton.isSelected())
                {
                    user = new Student(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            registrationTextField.getText().trim(),
                            0,
                            emailTextField.getText().trim());
                }
                else if (professorRadioButton.isSelected())
                {
                    List<Subject> subjects = new ArrayList<>();

                    user = new Professor(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            emailTextField.getText().trim(),
                            subjects);
                }
                else if (adminRadioButton.isSelected())
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
                    return new UserDB(connectDB.getConnection()).insertUser(user);
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
        {
            Main.closeCurrentStage(mainPane);
        });

        task.setOnFailed(workerStateEvent ->
        {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        createUserButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
    }


    public static List<MenuItem> initializeCheckMenuItemsFromList(List<Subject> subjectList)
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
