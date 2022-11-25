package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class CreateUserController
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
    private TextField emailTextField;

    @FXML
    private TextField registrationTextField;

    @FXML
    private MenuButton professorSubjects;

    @FXML
    private RadioButton professorRadioButton;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton studentRadioButton;

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

    }

    @FXML
    public void onRadioButtonChanged()
    {
        if (professorRadioButton.isSelected())
        {
            registrationTextField.setVisible(false);
            professorSubjects.setVisible(true);
            emailTextField.setVisible(true);
        }
        else if (studentRadioButton.isSelected())
        {
            professorSubjects.setVisible(false);
            registrationTextField.setVisible(true);
            emailTextField.setVisible(true);
        }
        else if (adminRadioButton.isSelected())
        {
            professorSubjects.setVisible(false);
            registrationTextField.setVisible(false);
            emailTextField.setVisible(false);
        }
    }

    @FXML
    public void onCreateButtonPressed()
    {
        if (!arePasswordsEqual())
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

                if (professorRadioButton.isSelected())
                {
                    List<Subject> subjects = new ArrayList<>();

                    user = new Professor(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            emailTextField.getText().trim(),
                            subjects);
                }
                else if (studentRadioButton.isSelected())
                {
                    user = new Student(usernameTextField.getText().trim(),
                            nameTextField.getText().trim(),
                            passwordField.getText(),
                            registrationTextField.getText().trim(),
                            0,
                            emailTextField.getText().trim());
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

                // Checks whether username already exists
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
        createUserButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
    }

    public boolean arePasswordsEqual()
    {
        return passwordField.getText().equals(confirmPasswordField.getText());
    }

}
