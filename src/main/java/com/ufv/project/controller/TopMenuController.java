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
    private ImageView userIcon;

    @FXML
    private Text userRole;

    public void initialize()
    {
        // Initialize default main image.
        pocIcon.setImage(new Image(new File("src/main/resources/com/ufv/project/images/POC_Main_Image.PNG").toURI().toString()));

        // Initialize default user image.
        userIcon.setImage(new Image(new File("src/main/resources/com/ufv/project/images/anonymous_user.png").toURI().toString()));

        // Initialize user role.
        userRole.setText("Unknown");
    }

    public ImageView getUserIcon()
    {
        return userIcon;
    }

    public void setUserIcon(Image userIcon)
    {
        this.userIcon.setImage(userIcon);
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