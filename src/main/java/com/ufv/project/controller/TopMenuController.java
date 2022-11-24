package com.ufv.project.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class TopMenuController
{
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView pocIcon;

    @FXML
    private ImageView userIcon;

    @FXML
    private Text userRole;

    @FXML
    public void initialize()
    {
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