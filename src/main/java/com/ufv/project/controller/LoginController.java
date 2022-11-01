package com.ufv.project.controller;

import com.ufv.project.model.*;
import com.ufv.project.db.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

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

        if (user == null)
        {
            return;
        }

        if (user.getPassword().equals(passwordField.getText()))
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

}