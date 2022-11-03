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
        this.nameText = new Text("Matt");
    }

    public Text getUsernameText()
    {
        return usernameText;
    }

    public void setUsernameText(Text usernameText)
    {
        this.usernameText = usernameText;
    }

    public Text getEmailText()
    {
        return emailText;
    }

    public void setEmailText(Text emailText)
    {
        this.emailText = emailText;
    }

    public Text getNameText()
    {
        return nameText;
    }

    public void setNameText(Text nameText)
    {
        this.nameText = nameText;
    }

    public void setController(CreatePOCController createPOCController)
    {

    }

}
