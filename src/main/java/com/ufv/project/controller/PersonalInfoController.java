package com.ufv.project.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PersonalInfoController
{
    @FXML
    private Text usernameText;

    @FXML
    private Text emailText;

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

    public String getEmailText()
    {
        return emailText.getText();
    }

    public void setEmailText(String email)
    {
        emailText.setText(email);
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
