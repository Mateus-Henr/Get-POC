package com.ufv.project.controller;

import com.ufv.project.model.*;
import com.ufv.project.db.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class LoginController
{
    @FXML
    private GridPane gridPane;

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
        // Set event handling to text box when pressing the "ENTER" key.
        usernameField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonClick();
            }
        });

        // Set event handling to text box when pressing the "ENTER" key.
        passwordField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && areFieldsValid())
            {
                onLoginButtonClick();
            }
        });

        // Initialize image.
        File file = new File("src/main/resources/com/ufv/project/images/POC_Main_Image.PNG");
        Image image = new Image(file.toURI().toString());
        mainImage.setImage(image);

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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ufv/project/create-poc-page-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.show();

                ((Stage) gridPane.getScene().getWindow()).close();
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