package com.ufv.project.controller;

import com.ufv.project.model.*;
import com.ufv.project.db.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class LoginController
{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView mainImage;

    @FXML
    public void initialize()
    {
        File file = new File("src/main/resources/com/ufv/project/images/POC_Main_Image.PNG");
        Image image = new Image(file.toURI().toString());
        mainImage.setImage(image);

        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        loginButton.setFocusTraversable(false);
    }

    @FXML
    protected void onLoginButtonClick()
    {
        User user = Singleton.getInstance().getUser(usernameField.getText());

        if (user == null)
        {
            return;
        }

        if (user.getPassword().equals(passwordField.getText()))
        {
            // Logged in successfully.
        }
        else
        {
            // Validation failed call fail style.
        }
    }


//    public boolean changeUsername(String currUsername, String newUsername)
//    {
//        User user = Singleton.getInstance().getUser(currUsername);
//
//        if (user == null)
//        {
//            return false;
//        }
//
//        if (!Singleton.getInstance().isUserInDatabase(newUsername))
//        {
//            user.setUsername(newUsername);
//
//            return true;
//        }
//
//        return false;
//    }

}