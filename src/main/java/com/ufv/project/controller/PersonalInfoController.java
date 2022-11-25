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
    private Text emailLabel;

    @FXML
    private Text emailText;

    @FXML
    private Text registrationLabel;

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
        UserTypesEnum userType = dataModel.getUserType();

        usernameText.setText(dataModel.getUsername());
        nameText.setText(dataModel.getName());
        userTypeText.setText(userType.toString());

        if (userType == UserTypesEnum.STUDENT)
        {
            emailText.setText(dataModel.getEmail());
            registrationText.setText(dataModel.getRegistration());
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            emailText.setText(dataModel.getEmail());

            registrationLabel.setManaged(false);
            registrationLabel.setVisible(false);
            registrationText.setManaged(false);
            registrationText.setVisible(false);
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            emailLabel.setManaged(false);
            emailLabel.setVisible(false);
            emailText.setManaged(false);
            emailText.setVisible(false);

            registrationLabel.setManaged(false);
            registrationLabel.setVisible(false);
            registrationText.setManaged(false);
            registrationText.setVisible(false);
        }
    }

}