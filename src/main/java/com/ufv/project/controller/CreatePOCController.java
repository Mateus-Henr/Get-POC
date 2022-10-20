package com.ufv.project.controller;

import com.ufv.project.model.*;
import com.ufv.project.db.Singleton;
import javafx.fxml.FXML;

import java.time.LocalDate;

public class CreatePOCController
{
    @FXML
    public boolean onAddPOC(String username, POC poc)
    {
        User user = Singleton.getInstance().getUser(username);

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

    @FXML
    public boolean updatePOCTitle(String studentUsername, String newTitle)
    {
        User user = Singleton.getInstance().getUser(studentUsername);

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
        User user = Singleton.getInstance().getUser(studentUsername);

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
        User user = Singleton.getInstance().getUser(studentUsername);

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
        User user = Singleton.getInstance().getUser(studentUsername);

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

}
