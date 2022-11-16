package com.ufv.project.db;

import com.ufv.project.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDataSingleton
{
    private String username;
    private String name;
    private String userType;

    private UserDataSingleton()
    {
        try (ConnectDB connectDB = new ConnectDB())
        {
            Connection conn = connectDB.getConnection();
            UserDB userDB = new UserDB(conn);

            User user = userDB.queryUserByID("Miguel");

            if (user != null)
            {
                username = user.getUsername();
                name = user.getName();
                userType = user.getUserType().toString();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

}