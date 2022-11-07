package com.ufv.project.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class TopMenuController
{
    @FXML
    private ImageView pocIcon;

    @FXML
    private ImageView userPicture;

    @FXML
    private Text userRole;

    public void initialize()
    {
        // Initialize image.
        File file = new File("src/main/resources/com/ufv/project/images/POC_Main_Image.PNG");
        Image image = new Image(file.toURI().toString());
        pocIcon.setImage(image);
    }

    public ImageView getUserPicture()
    {
        return userPicture;
    }

    public void setUserPicture(Image userPicture)
    {
        this.userPicture.setImage(userPicture);
    }

    public Text getUserRole()
    {
        return userRole;
    }

    public void setUserRole(String userRole)
    {
        this.userRole.setText(userRole);
    }

}