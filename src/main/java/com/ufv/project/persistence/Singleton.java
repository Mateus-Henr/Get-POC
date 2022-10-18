package com.ufv.project.persistence;

import com.ufv.project.model.POC;
import com.ufv.project.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Singleton
{
    private final List<User> userList;
    private final List<POC> pocList;

    private Singleton()
    {
        userList = new ArrayList<>();
        pocList = new ArrayList<>();
    }

    private static class RegistryHolder
    {
        static Singleton INSTANCE = new Singleton();

    }

    public static Singleton getInstance()
    {
        return RegistryHolder.INSTANCE;
    }

    public List<User> getUserList()
    {
        return Collections.unmodifiableList(userList);
    }

    public List<POC> getPocList()
    {
        return Collections.unmodifiableList(pocList);
    }

    public boolean addUser(User user)
    {
        return userList.add(user);
    }

    public User getUser(String username)
    {
        for (User user : userList)
        {
            if (user.getUsername().equals(username))
            {
                return user;
            }
        }

        return null;
    }

    public boolean removeUser(String username)
    {
        return userList.removeIf(user -> user.getUsername().equals(username));
    }

    public boolean addPOC(POC poc)
    {
        return pocList.add(poc);
    }

    public POC getPOC(int pocId)
    {
        for (POC poc : pocList)
        {
            if (poc.getId() == pocId)
            {
                return poc;
            }
        }

        return null;
    }

    public boolean removePOC(int pocId)
    {
        return pocList.removeIf(poc -> poc.getId() == pocId);
    }

}
