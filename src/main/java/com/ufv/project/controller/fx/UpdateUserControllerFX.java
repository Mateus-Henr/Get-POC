package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.controller.java.UpdateUserController;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.POCDB;
import com.ufv.project.db.SubjectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserControllerFX
{
    @FXML
    private VBox mainPane;

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
    private Button updateUserButton;

    @FXML
    private ProgressIndicator progressIndicator;

    public static final int MAX_NUMBER_CHARACTERS = 100;

    private final DataModel dataModel;

    private User user;

    /**
     * Constructor for UpdateUserControllerFX.
     */
    public UpdateUserControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    /**
     * Runs upon initialization.
     */
    @FXML
    public void initialize()
    {
        usernameTextField.textProperty().addListener((ov, oldValue, newValue) ->
        {
            if (usernameTextField.getText().length() > MAX_NUMBER_CHARACTERS)
            {
                usernameTextField.setText(usernameTextField.getText().substring(0, MAX_NUMBER_CHARACTERS));
            }
        });

        nameTextField.textProperty().addListener((ov, oldValue, newValue) ->
        {
            if (nameTextField.getText().length() > MAX_NUMBER_CHARACTERS)
            {
                nameTextField.setText(nameTextField.getText().substring(0, MAX_NUMBER_CHARACTERS));
            }
        });

        POCIDTextField.textProperty().addListener((ov, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*"))
            {
                POCIDTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (newValue.matches("\\d{9,}"))
            {
                POCIDTextField.setText(newValue.substring(0, 9));
            }
        });

        registrationTextField.textProperty().addListener((ov, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*"))
            {
                registrationTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (newValue.matches("\\d{4,}"))
            {
                registrationTextField.setText(newValue.substring(0, 4));
            }
        });
    }

    /**
     * Updates User with given data.
     */
    @FXML
    public void onUpdateButtonClicked()
    {
        if (!UpdateUserController.arePasswordsEqual(passwordField.getText(), confirmPasswordField.getText()))
        {
            passwordField.getStyleClass().add("text-field-invalid");
            confirmPasswordField.getStyleClass().add("text-field-invalid");

            return;
        }
        else
        {
            passwordField.getStyleClass().removeIf(s -> s.equals("text-field-invalid"));
            confirmPasswordField.getStyleClass().removeIf(s -> s.equals("text-field-invalid"));
        }

        UserTypesEnum userType = user.getUserType();

        if (userType == UserTypesEnum.PROFESSOR || userType == UserTypesEnum.STUDENT)
        {
            if (!UpdateUserController.checkEmail(emailTextField.getText()))
            {
                emailTextField.getStyleClass().add("text-field-invalid");

                return;
            }
            else
            {
                emailTextField.getStyleClass().removeIf(s -> s.equals("text-field-invalid"));
            }
        }

        if (userType == UserTypesEnum.STUDENT)
        {
            if (!UpdateUserController.checkRegistration(registrationTextField.getText()))
            {
                registrationTextField.getStyleClass().add("create-text-field-invalid");

                return;
            }
            else
            {
                registrationTextField.getStyleClass().removeIf(s -> s.equals("create-text-field-invalid"));
            }
        }

        int POCID = 0;

        if (!POCIDTextField.getText().trim().isEmpty() && user.getUserType() == UserTypesEnum.STUDENT)
        {
            POCID = Integer.parseInt(POCIDTextField.getText().trim());

            try (ConnectDB connectDB = new ConnectDB())
            {
                if (!new POCDB(connectDB.getConnection()).checkIfPOCExists(Integer.parseInt(POCIDTextField.getText().trim())))
                {
                    POCIDTextField.getStyleClass().add("text-field-invalid");

                    return;
                }
                else
                {
                    POCIDTextField.getStyleClass().removeIf(s -> s.equals("text-field-invalid"));
                }
            }
            catch (SQLException e)
            {
                new Alert(Alert.AlertType.ERROR,
                        "Couldn't get POC from database: " + e.getMessage(),
                        ButtonType.OK).showAndWait();
            }
        }

        int finalPOCID = POCID;

        final Task<User> task = new Task<>()
        {
            @Override
            protected User call() throws SQLException
            {
                User updatedUser = null;

                UserTypesEnum userType = user.getUserType();

                if (userType == UserTypesEnum.STUDENT)
                {
                    updatedUser = new Student(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            registrationTextField.getText().trim(),
                            finalPOCID,
                            emailTextField.getText().trim());
                }
                else if (userType == UserTypesEnum.PROFESSOR)
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

                        updatedUser = new Professor(usernameTextField.getText().trim(),
                                nameTextField.getText().trim(),
                                passwordField.getText(),
                                emailTextField.getText().trim(),
                                subjectList);
                    }
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    updatedUser = new Administrator(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText());
                }

                if (updatedUser == null)
                {
                    throw new SQLException("ERROR: Couldn't get user to update.");
                }

                try (ConnectDB connectDB = new ConnectDB())
                {
                    return new UserDB(connectDB.getConnection()).updateUser(updatedUser);
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
                Main.loadNewSceneWithDataModel(mainPane, "search-user-page-view.fxml", dataModel, "Update User"));

        task.setOnFailed(workerStateEvent -> new Alert(Alert.AlertType.ERROR,
                "Couldn't update user: " + task.getException().getMessage(),
                ButtonType.OK).showAndWait());

        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        updateUserButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));

        new Thread(task).start();
    }

    /**
     * Shows the number of subject selected.
     */
    @FXML
    public void onSelectSubject()
    {
        professorSubjects.setText(professorSubjects.getItems().stream()
                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                .count() + " selected");
    }

    /**
     * Initializes a list of MenuItems from a list of Subjects.
     *
     * @param subjectList list containing the Subject to be displayed on the MenuItems.
     * @return list containing initialized MenuItems from a list of Subjects.
     */
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

    /**
     * Sets up data for the User to be updated.
     *
     * @param user user to set up data.
     */
    public void setUserData(User user)
    {
        this.user = user;

        usernameTextField.setText(user.getUsername());
        nameTextField.setText(user.getName());

        UserTypesEnum userType = user.getUserType();

        if (userType == UserTypesEnum.ADMIN)
        {
            POCIDLabel.setManaged(false);
            POCIDTextField.setManaged(false);
            POCIDLabel.setVisible(false);
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
        else if (userType == UserTypesEnum.STUDENT)
        {
            POCIDLabel.setManaged(true);
            POCIDTextField.setManaged(true);
            POCIDLabel.setVisible(true);
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
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            POCIDLabel.setManaged(false);
            POCIDTextField.setManaged(false);
            POCIDLabel.setVisible(false);
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
        }

        if (userType == UserTypesEnum.STUDENT)
        {
            Student student = (Student) user;

            registrationTextField.setText(student.getRegistration());
            emailTextField.setText(student.getEmail());
            POCIDTextField.setText(String.valueOf(student.getPOCID()));
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            Professor professor = (Professor) user;

            emailTextField.setText(professor.getEmail());

            List<Subject> markedSubjects = professor.getSubjectsTaught();

            professorSubjects.getItems().setAll(initializeCheckMenuItemsFromList(professor.getSubjectsTaught()));

            for (MenuItem menuItem : professorSubjects.getItems())
            {
                markedSubjects.stream().map(Subject::getId).forEach(integer -> ((CheckMenuItem) menuItem)
                        .setSelected(integer == Integer.parseInt(menuItem.getId()) || ((CheckMenuItem) menuItem).isSelected()));
            }
        }
    }

}
