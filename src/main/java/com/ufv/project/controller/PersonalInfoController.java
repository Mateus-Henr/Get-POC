package com.ufv.project.controller;

import com.ufv.project.model.DataModel;
import com.ufv.project.model.UserTypesEnum;
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

    @FXML
    private Text emailText;

    @FXML
    private Text registrationText;

    private final DataModel dataModel;

    // Student
    public PersonalInfoController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {
        usernameText.setText(dataModel.getUsername());
        nameText.setText(dataModel.getName());
        userTypeText.setText(dataModel.getUserType());

        if (userTypeText.getText().equalsIgnoreCase(UserTypesEnum.STUDENT.toString()))
        {
            emailText.setText(dataModel.getEmail());
            registrationText.setText(dataModel.getRegistration());

            registrationText.setManaged(false);
            registrationText.setVisible(false);
        }
        else if (userTypeText.getText().equalsIgnoreCase(UserTypesEnum.PROFESSOR.toString()))
        {
            emailText.setText(dataModel.getEmail());
        }
        else if (userTypeText.getText().equalsIgnoreCase(UserTypesEnum.ADMIN.toString()))
        {
            emailText.setManaged(false);
            emailText.setVisible(false);

            registrationText.setManaged(false);
            registrationText.setVisible(false);
        }
    }

}