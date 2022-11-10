package com.ufv.project.db;

import java.sql.*;

import com.ufv.project.model.Subject;

public class ConnectDB
{
    public static final String DB_NAME = "get_poc";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    private Connection conn;


    public boolean open()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't connect to database " + e.getMessage());
            return false;
        }
    }

    public void close()
    {
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public Connection getConnection()
    {
        return conn;
    }


    public static void main(String[] args) throws SQLException
    {
        ConnectDB connectDB = new ConnectDB();

        if (!connectDB.open())
        {
            return;
        }

        System.out.println("Connection to Mysql has been established.");

        SubjectDB subjectDB = new SubjectDB(connectDB.getConnection());
        Subject subject = new Subject(0, "teste", "teste");

        int id = subjectDB.insertSubject(subject);

        System.out.println("Subject inserted with id: " + id);

        connectDB.close();
    }


}
