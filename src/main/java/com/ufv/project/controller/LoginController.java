package com.ufv.project.controller;

import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController
{
    @FXML
    public AnchorPane mainPane;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text invalidText;

    @FXML
    public void initialize()
    {
        // Set event handling to text box when pressing the "ENTER" key.
        usernameField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonAction();
            }
        });

        // Set event handling to password box when pressing the "ENTER" key.
        passwordField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonAction();
            }
        });

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

        try (ConnectDB connectDB = new ConnectDB())
        {
            user = new UserDB(connectDB.getConnection()).queryUserByID(usernameField.getText());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        if (user == null)
        {
            return;
        }

        if (user.getPassword().equals(passwordField.getText()))
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ufv/project/fxml/create-poc-page-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.show();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            usernameField.getStyleClass().add("username-password-fields-invalid");
            passwordField.getStyleClass().add("username-password-fields-invalid");
            invalidText.setOpacity(1);
        }
    }

    public boolean areFieldsValid()
    {
        return !usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty();
    }

}