package com.ufv.project.db;

import javafx.scene.text.Text;

public class UserDataSingleton
{
    private Text username;
    private Text email;
    private Text name;

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

    public Text getUsername()
    {
        return username;
    }

    public void setUsername(Text username)
    {
        this.username = username;
    }

    public Text getEmail()
    {
        return email;
    }

    public void setEmail(Text email)
    {
        this.email = email;
    }

    public Text getName()
    {
        return name;
    }

    public void setName(Text name)
    {
        this.name = name;
    }

}
