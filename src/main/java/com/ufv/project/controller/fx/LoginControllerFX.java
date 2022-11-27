package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LoginControllerFX
{
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text invalidText;

    @FXML
    private ProgressIndicator progressIndicator;

    private static final String INVALID_BOX_CSS_CLASS = "username-password-fields-invalid";

    @FXML
    public void initialize()
    {
        // Set event handling to text box when pressing the "ENTER" key.
        loginButton.disableProperty().bind(Bindings.createBooleanBinding(() ->
                                usernameField.getText().trim().isEmpty(),
                        usernameField.textProperty())
                .or(Bindings.createBooleanBinding(() ->
                                passwordField.getText().trim().isEmpty(),
                        passwordField.textProperty())
                ));

        // Take away focus from boxes.
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        loginButton.setFocusTraversable(false);
    }

    @FXML
    protected void onLoginButtonPressed()
    {
        loginButton.getStyleClass().add("login-button-clicked");
    }

    @FXML
    protected void onLoginButtonReleased()
    {
        loginButton.getStyleClass().remove("login-button-clicked");
    }

    @FXML
    protected void onLoginButtonAction()
    {
        final Task<User> task = new Task<>()
        {
            @Override
            protected User call() throws Exception
            {
                try (ConnectDB connectDB = new ConnectDB())
                {
                    return new UserDB(connectDB.getConnection()).queryUserByID(usernameField.getText().trim());
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
        {
            User user = task.getValue();

            if (user == null || !user.getPassword().equals(passwordField.getText()))
            {
                usernameField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
                passwordField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
                invalidText.setVisible(true);

                return;
            }


            usernameField.getStyleClass().removeIf(s -> s.equals(INVALID_BOX_CSS_CLASS));
            passwordField.getStyleClass().removeIf(s -> s.equals(INVALID_BOX_CSS_CLASS));
            invalidText.setVisible(false);

            DataModel dataModel = null;

            if (user.getUserType() == UserTypesEnum.STUDENT)
            {
                dataModel = new DataModel((Student) user);
            }
            else if (user.getUserType() == UserTypesEnum.PROFESSOR)
            {
                dataModel = new DataModel((Professor) user);
            }
            else if (user.getUserType() == UserTypesEnum.ADMIN)
            {
                dataModel = new DataModel((Administrator) user);
            }

            if (dataModel == null)
            {
                return;
            }

            Main.loadStageWithDataModel("search-poc-page-view.fxml", dataModel, "Search POC");
            Main.closeCurrentStage(mainPane);
        });

        new Thread(task).start();
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        loginButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
    }

}