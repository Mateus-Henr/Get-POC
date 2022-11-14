package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public void initialize()
    {
        // Set event handling to text box when pressing the "ENTER" key.
        usernameField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonClick();
            }
        });

        // Set event handling to password box when pressing the "ENTER" key.
        passwordField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonClick();
            }
        });

        // Take away focus from boxes.
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        loginButton.setFocusTraversable(false);
    }

    @FXML
    protected void onLoginButtonClick()
    {
        User user = Singleton.getInstance().getUser(usernameField.getText());

//        if (user == null)
//        {
//            return;
//        }

        if ("test".equals(passwordField.getText()))
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
            // Validation failed call fail style.
        }
    }

    public boolean areFieldsValid()
    {
        return !usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty();
    }

}