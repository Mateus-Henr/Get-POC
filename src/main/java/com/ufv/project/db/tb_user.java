package com.ufv.project.db;


import static com.ufv.project.db.ConnectDB.connect;

import java.sql.*;

public class tb_user
{

    /*
     *   TB_User table columns names
     */
    public static final String TABLE_USER = "TB_User";
    public static final String COLUMN_USER_ID = "ID";
    public static final String COLUMN_USER_PASSWORD = "Password";
    public static final String COLUMN_USER_NAME = "Name";
    public static final String COLUMN_USER_TYPE = "Type";

    /*
     * Getters and Setters
     */

    public static String getUserName(String id)
    {
        String name = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            while (resultSet.next())
            {
                name = resultSet.getString(COLUMN_USER_NAME);
            }
            resultSet.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return name;
    }

    public static String getUserPassword(String id)
    {
        String password = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_USER_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            while (resultSet.next())
            {
                password = resultSet.getString(COLUMN_USER_PASSWORD);
            }
            resultSet.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return password;
    }

    public static String getUserType(String id)
    {
        String type = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_USER_TYPE + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            while (resultSet.next())
            {
                type = resultSet.getString(COLUMN_USER_TYPE);
            }
            resultSet.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return type;
    }

    public static void setUserName(String id, String name)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_NAME + " = '" + name + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setUserPassword(String id, String password)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = '" + password + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setUserType(String id, String type)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_TYPE + " = '" + type + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /*
     * Methods
     */

    public static void addUser(String id, String password, String name, String type)
    {
        /*
         * This method adds a user to the database
         */

        String sql = "INSERT INTO " +
                TABLE_USER + " (" + COLUMN_USER_ID + ", " +
                COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, type);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void dropUser(String id)
    {
        /*
         * This method drops a user from the database
         */

        String sql = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllUsers()
    {
        /*
         * This method prints all users in the database
         */
        String sql = "SELECT * FROM " + TABLE_USER;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                System.out.println("ID: " + rs.getString(COLUMN_USER_ID) + "\n" +
                        "Password: " + rs.getString(COLUMN_USER_PASSWORD) + "\n" +
                        "Name: " + rs.getString(COLUMN_USER_NAME) + "\n" +
                        "Type: " + rs.getString(COLUMN_USER_TYPE));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printUserById(String id)
    {
        /*
         * This method prints a user in the database
         */
        System.out.println("ID: " + id + "\n" +
                "Password: " + getUserPassword(id) + "\n" +
                "Name: " + getUserName(id) + "\n" +
                "Type: " + getUserType(id));
    }

}




