package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.List;

public class UserDB
{
    private static final String TABLE_USER = "TB_User";
    private static final String COLUMN_USER_ID = "ID";
    private static final String COLUMN_USER_PASSWORD = "Password";
    private static final String COLUMN_USER_NAME = "Name";
    private static final String COLUMN_USER_TYPE = "Type";

    private static final int COLUMN_USER_ID_INDEX = 1;
    private static final int COLUMN_USER_PASSWORD_INDEX = 2;
    private static final int COLUMN_USER_NAME_INDEX = 3;
    private static final int COLUMN_USER_TYPE_INDEX = 4;

    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_STUDENT_REGISTRATION = "Registration";
    private static final int COLUMN_STUDENT_EMAIL_INDEX = 1;
    private static final int COLUMN_STUDENT_REGISTRATION_INDEX = 2;


    private static final String INSERT_USER = "INSERT INTO " +
            TABLE_USER + " (" + COLUMN_USER_ID + ", " +
            COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";

    private static final String GET_USER = "SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

    private Connection conn;

    private PreparedStatement getUser;
    private PreparedStatement insertUser;

    public UserDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getUser = conn.prepareStatement(GET_USER);
            insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public User getUserByID(String id) throws SQLException
    {
        getUser.setString(COLUMN_USER_ID_INDEX, id);

        try (ResultSet resultSet = getUser.executeQuery())
        {
            if (resultSet.next())
            {
                UserTypesEnum userType = UserTypesEnum.values()[resultSet.getInt(COLUMN_USER_TYPE_INDEX)];

                if (userType == UserTypesEnum.PROFESSOR)
                {
                    return new Professor(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                            resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                            getSubjectsTaughtByProfessorID(resultSet.getString(COLUMN_USER_ID_INDEX)));
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    return new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                }
                else if (userType == UserTypesEnum.STUDENT)
                {
                    return new Student(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                            resultSet.getString(COLUMN_STUDENT_REGISTRATION_INDEX),
                            resultSet.getString(COLUMN_STUDENT_EMAIL_INDEX));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String insertUser(User user) throws SQLException
    {
        UserTypesEnum userType = user.getUserType();

        if (userType == UserTypesEnum.PROFESSOR)
        {
            return new ProfessorDB(conn).insertProfessor((Professor) user);
        }
        else if (userType == UserTypesEnum.STUDENT)
        {
//            return new StudentDB(conn).insertStudent((Student) user);
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
//            return new AdministratorDB(conn).insertAdmin((Administrator) user);
        }

        return null;
    }

    public int deleteUser(String id)
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




