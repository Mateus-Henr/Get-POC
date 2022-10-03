package com.ufv.project.model;

public class User
{
    protected int id;
    protected String firstName;
    protected String lastName;
    protected final int CPF;
    protected String password; // Remove

    public User(String firstName, String lastName, int CPF, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CPF = CPF;
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
