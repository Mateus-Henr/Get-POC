/*package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.ArrayList;
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


    private static final String GET_ALL_USERS = "SELECT * FROM " + TABLE_USER;

    private static final String INSERT_USER = "INSERT INTO " +
            TABLE_USER + " (" + COLUMN_USER_ID + ", " +
            COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";

    private static final String GET_USER = "SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

    private static final String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

    private Connection conn;

    private PreparedStatement getUser;
    private PreparedStatement insertUser;
    private PreparedStatement deleteUser;

    public UserDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getUser = conn.prepareStatement(GET_USER);
            insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            deleteUser = conn.prepareStatement(DELETE_USER);
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
                    return new ProfessorDB(conn).getProfessorByID(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
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
        insertUser.setString(COLUMN_USER_ID_INDEX, user.getUsername());
        insertUser.setString(COLUMN_USER_PASSWORD_INDEX, user.getPassword());
        insertUser.setString(COLUMN_USER_NAME_INDEX, user.getName());
        insertUser.setInt(COLUMN_USER_TYPE_INDEX, user.getUserType().ordinal());

        try (ResultSet resultSet = insertUser.executeQuery())
        {
            UserTypesEnum userType = user.getUserType();

            if (resultSet.next())
            {
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
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public User deleteUser(String id) throws SQLException
    {
        deleteUser.setString(COLUMN_USER_ID_INDEX, id);

        try (ResultSet resultSet = deleteUser.executeQuery())
        {
            UserTypesEnum userType = UserTypesEnum.values()[resultSet.getInt(COLUMN_USER_TYPE_INDEX)];

            if (resultSet.next())
            {
                if (userType == UserTypesEnum.PROFESSOR)
                {
                    return new ProfessorDB(conn).getProfessorByID(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                }
                else if (userType == UserTypesEnum.STUDENT)
                {
//            return new StudentDB(conn).insertStudent((Student) user);
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
//            return new AdministratorDB(conn).insertAdmin((Administrator) user);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUser()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS))
        {
            List<User> users = new ArrayList<>();

            while (resultSet.next())
            {
                UserTypesEnum userType = UserTypesEnum.values()[resultSet.getInt(COLUMN_USER_TYPE_INDEX)];

                if (userType == UserTypesEnum.PROFESSOR)
                {
                    users.add(new ProfessorDB(conn).getProfessorByID(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
                else if (userType == UserTypesEnum.STUDENT)
                {
//            return new StudentDB(conn).insertStudent((Student) user);
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
//            return new AdministratorDB(conn).insertAdmin((Administrator) user);
                }

                return users;
            }

            return users;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

}*/