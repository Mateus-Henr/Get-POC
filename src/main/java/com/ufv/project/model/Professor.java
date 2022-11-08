package com.ufv.project.model;

import java.util.List;

public class Professor extends User
{
    private String email;
    private List<Subject> subjectsTaught;

    public Professor(String username, String name, String password, String email, List<Subject> subjectsTaught)
    {
        super(username, name, password, UserTypesEnum.PROFESSOR);
        this.email = email;
        this.subjectsTaught = subjectsTaught;
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

}