package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.db.UserDataSingleton;
import com.ufv.project.model.User;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController
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
    private ProgressBar progressBar;

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
                    return new UserDB(connectDB.getConnection()).queryUserByID(usernameField.getText());
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

            // Sets global data
            UserDataSingleton.getInstance().initialiseUser(usernameField.getText());

            Main.loadStage("create-poc-page-view.fxml", "Create POC");
            Main.closeCurrentStage(mainPane);
        });

        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        loginButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
    }

}