package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.db.UserDataSingleton;
import com.ufv.project.model.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        User user = null;

        try (ConnectDB connectDB = new ConnectDB();
            UserDB userDB = new UserDB(connectDB.getConnection()))
        {
            user = userDB.queryUserByID(usernameField.getText());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        if (user == null)
        {
            usernameField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
            passwordField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
            invalidText.setVisible(true);
            return;
        }

        if (user.getPassword().equals(passwordField.getText()))
        {
            try
            {
                usernameField.getStyleClass().removeIf(s -> s.equals(INVALID_BOX_CSS_CLASS));
                passwordField.getStyleClass().removeIf(s -> s.equals(INVALID_BOX_CSS_CLASS));
                invalidText.setVisible(false);

                // Sets global data
                UserDataSingleton.getInstance().initialiseUser(usernameField.getText());

                Main.loadStage("create-poc-page-view.fxml");
                Main.closeCurrentStage(mainPane);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            usernameField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
            passwordField.getStyleClass().add(INVALID_BOX_CSS_CLASS);
            invalidText.setVisible(true);
        }
    }

}