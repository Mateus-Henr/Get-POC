package com.ufv.project.model;

public class Administrator extends User
{
    public Administrator(String username, String name, String password)
    {
        super(username, name, password, UserTypesEnum.ADMIN);
    }

    @Override
    public boolean canModifyUsers()
    {
        return true;
    }

    @Override
    public boolean canModifyPOCs()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return getUserType().toString() + " -> " + getUsername() + " - " + getName();
    }

}