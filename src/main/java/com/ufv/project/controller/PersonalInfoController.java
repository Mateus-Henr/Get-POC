package com.ufv.project.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PersonalInfoController
{
    @FXML
    private Text usernameText;

    @FXML
    private Text userTypeText;

    @FXML
    private Text nameText;

    public void initialize()
    {

    }

    public String getUsernameText()
    {
        return usernameText.getText();
    }

    public void setUsernameText(String username)
    {
        usernameText.setText(username);
    }

    public String getUserTypeText()
    {
        return userTypeText.getText();
    }

    public void setUserTypeText(String userType)
    {
        userTypeText.setText(userType);
    }

    public String getNameText()
    {
        return nameText.getText();
    }

    public void setNameText(String name)
    {
        nameText.setText(name);
    }

}