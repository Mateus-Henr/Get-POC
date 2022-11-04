package com.ufv.project.db;

public class UserDataSingleton
{
    private String username;
    private String email;
    private String name;

    private UserDataSingleton()
    {

    }

    private static class RegistryHolder
    {
        static UserDataSingleton INSTANCE = new UserDataSingleton();

    }

    public static UserDataSingleton getInstance()
    {
        return UserDataSingleton.RegistryHolder.INSTANCE;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
