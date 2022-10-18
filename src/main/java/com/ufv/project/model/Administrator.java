package com.ufv.project.model;

import com.ufv.project.persistence.Singleton;

public class Administrator extends User
{
    public Administrator(String username, String name, String password)
    {
        super(username, name, password);
    }

    public User getUser(String username)
    {
        return Singleton.getInstance().getUser(username);
    }

    public boolean removeUser(String username)
    {
        return Singleton.getInstance().removeUser(username);
    }

    public boolean addUser(User user)
    {
        return Singleton.getInstance().addUser(user);
    }

}
