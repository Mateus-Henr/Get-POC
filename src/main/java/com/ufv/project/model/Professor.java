package com.ufv.project.model;

import java.time.LocalDate;
import java.util.List;

public class Professor extends User
{
    private String email;
    private List<Subject> subjectsTaught;

    public Professor(String username, String name, String password, String email, List<Subject> subjectsTaught)
    {
        super(username, name, password);
        this.email = email;
        this.subjectsTaught = subjectsTaught;
    }

    public boolean addPOC(String studentUsername, POC poc)
    {
        return false;
    }

    public boolean updatePOCTitle(String studentUsername, String newTitle)
    {
        return false;
    }

    public boolean updatePOCDefenseDate(String studentUsername, LocalDate newDefenseDate)
    {
        return false;
    }

    public boolean updatePOCSummary(String studentUsername, String newSummary)
    {
        return false;
    }

    public boolean updatePOCField(String studentUsername, Field newField)
    {
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
