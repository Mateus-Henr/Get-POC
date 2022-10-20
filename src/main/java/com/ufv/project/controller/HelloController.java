package com.ufv.project.controller;

import com.ufv.project.model.*;
import com.ufv.project.persistence.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class HelloController
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private User getUserFromDatabase(String username)
    {
        return Singleton.getInstance().getUser(username);
    }

    public boolean addPOC(String username, POC poc)
    {
        User user = getUserFromDatabase(username);

        if (user == null)
        {
            return false;
        }

        if (user.canModifyPOCs())
        {
            return Singleton.getInstance().addPOC(poc);
        }

        return false;
    }

    public boolean updatePOCTitle(String studentUsername, String newTitle)
    {
        User user = getUserFromDatabase(studentUsername);

        if (user == null)
        {
            return false;
        }

        if (user.canModifyPOCs() && user.getUserType() == UserTypesEnum.STUDENT)
        {
            POC poc = ((Student) user).getPoc();

            if (poc != null)
            {
                poc.setTitle(newTitle);

                return true;
            }
        }

        return false;
    }

    public boolean updatePOCDefenseDate(String studentUsername, LocalDate newDefenseDate)
    {
        User user = getUserFromDatabase(studentUsername);

        if (user == null)
        {
            return false;
        }

        if (user.canModifyPOCs() && user.getUserType() == UserTypesEnum.STUDENT)
        {
            POC poc = ((Student) user).getPoc();

            if (poc != null)
            {
                poc.setDefenseDate(newDefenseDate);

                return true;
            }
        }

        return false;
    }

    public boolean updatePOCSummary(String studentUsername, String newSummary)
    {
        User user = getUserFromDatabase(studentUsername);

        if (user == null)
        {
            return false;
        }

        if (user.canModifyPOCs() && user.getUserType() == UserTypesEnum.STUDENT)
        {
            POC poc = ((Student) user).getPoc();

            if (poc != null)
            {
                poc.setSummary(newSummary);

                return true;
            }
        }

        return false;
    }

    public boolean updatePOCField(String studentUsername, Field newField)
    {
        User user = getUserFromDatabase(studentUsername);

        if (user == null)
        {
            return false;
        }

        if (user.canModifyPOCs() && user.getUserType() == UserTypesEnum.STUDENT)
        {
            POC poc = ((Student) user).getPoc();

            if (poc != null)
            {
                poc.setField(newField);

                return true;
            }
        }

        return false;
    }

    public boolean updatePOCPDF(String studentUsername, PDF newPDF)
    {
        return false;
    }

    public boolean updatePOCRegistrant(String studentUsername, Professor newRegistrant)
    {
        return false;
    }

    public boolean updatePOCAdvisor(String studentUsername, Professor newAdvisor)
    {
        return false;
    }

    public boolean addCoAdvisorToPOC(String studentUsername, Professor newCoAdvisor)
    {
        return false;
    }

    public boolean removeCoAdvisorFromPOC(String studentUsername, Professor newCoAdvisor)
    {
        return false;
    }

    public boolean removePOC(String studentUsername)
    {
        return false;
    }

    public User getUser(String username)
    {
        return Singleton.getInstance().getUser(username);
    }

    public boolean removeUser(String username)
    {
        return Singleton.getInstance().removeUser(username);
    }

    public boolean addUser(User user)
    {
        return Singleton.getInstance().addUser(user);
    }

    public boolean changeUsername(String currUsername, String newUsername)
    {
        User user = Singleton.getInstance().getUser(currUsername);

        if (user == null)
        {
            return false;
        }

        if (!Singleton.getInstance().isUserInDatabase(newUsername))
        {
            user.setUsername(newUsername);

            return true;
        }

        return false;
    }

}