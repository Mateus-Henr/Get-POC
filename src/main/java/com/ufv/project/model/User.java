package com.ufv.project.model;

public class User
{
    private String username;
    private String name;
    private String password;
    private final UserTypesEnum userType;

    public User(String username, String name, String password, UserTypesEnum userType)
    {
        this.username = username;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserTypesEnum getUserType()
    {
        return userType;
    }

    public boolean canModifyUsers()
    {
        return false;
    }

    public boolean canModifyPOCs()
    {
        return false;
    }

}
