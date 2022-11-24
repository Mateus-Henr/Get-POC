package com.ufv.project.model;

import java.util.List;

public class Professor extends User
{
    private final String email;
    private final List<Subject> subjectsTaught;

    public Professor(String username, String name, String password, String email, List<Subject> subjectsTaught)
    {
        super(username, name, password, UserTypesEnum.PROFESSOR);
        this.email = email;
        this.subjectsTaught = subjectsTaught;
    }

    public String getEmail()
    {
        return email;
    }

    public List<Subject> getSubjectsTaught()
    {
        return subjectsTaught;
    }

    @Override
    public boolean canModifyUsers()
    {
        return false;
    }

    @Override
    public boolean canModifyPOCs()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return getName();
    }

}