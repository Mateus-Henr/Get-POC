package com.ufv.project.model;

import com.ufv.project.persistence.Singleton;

public class User
{
    protected String username;
    protected String name;
    protected String password;

    public User(String username, String name, String password)
    {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean changeUsername(String newUsername)
    {
        User foundUser = Singleton.getInstance().getUser(newUsername);

        if (foundUser == null)
        {
            this.username = newUsername;

            return true;
        }

        return false;
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

}
