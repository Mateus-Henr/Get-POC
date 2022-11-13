package com.ufv.project.db;

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

    private static final String QUERY_USER = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    private static final String QUERY_USERS = "SELECT * FROM " + TABLE_USER;
    public static final String INSERT_USER = "INSERT INTO " + TABLE_USER + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_TYPE + " = ? WHERE " + COLUMN_USER_ID + " = ?";
    private static final String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

    private Connection conn;

    private PreparedStatement queryUser;
    private PreparedStatement queryUsers;
    private PreparedStatement insertUser;
    private PreparedStatement updateUser;
    private PreparedStatement deleteUser;

    public UserDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            queryUser = conn.prepareStatement(QUERY_USER);
            queryUsers = conn.prepareStatement(QUERY_USERS);
            insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            deleteUser = conn.prepareStatement(DELETE_USER);
            updateUser = conn.prepareStatement(UPDATE_USER);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public User queryUserByID(String id) throws SQLException
    {
        queryUser.setString(COLUMN_USER_ID_INDEX, id);

        try (ResultSet resultSet = queryUser.executeQuery())
        {
            if (resultSet.next())
            {
                UserTypesEnum userType = UserTypesEnum.valueOf(resultSet.getString(COLUMN_USER_TYPE_INDEX));

                if (userType == UserTypesEnum.PROFESSOR)
                {
                    return new ProfessorDB(conn).queryProfessor(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX));
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    return new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                }
                else if (userType == UserTypesEnum.STUDENT)
                {
                    return new StudentDB(conn).queryStudent(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
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
        insertUser.setString(COLUMN_USER_TYPE_INDEX, user.getUserType().toString());

        int affectedRows = insertUser.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't insert user!");
        }

        UserTypesEnum userType = user.getUserType();

        if (userType == UserTypesEnum.PROFESSOR)
        {
            return new ProfessorDB(conn).insertProfessor((Professor) user);
        }
        else if (userType == UserTypesEnum.STUDENT)
        {
            return new StudentDB(conn).insertStudent((Student) user);
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            return user.getUsername();
        }

        return null;
    }

    public User deleteUserByID(String id) throws SQLException
    {
        User foundUser = queryUserByID(id);

        if (foundUser == null)
        {
            return null;
        }

        if (foundUser.getUserType() == UserTypesEnum.PROFESSOR)
        {
            new ProfessorDB(conn).deleteProfessor(foundUser.getUsername(),
                    foundUser.getName(),
                    foundUser.getPassword());
        }
        else if (foundUser.getUserType() == UserTypesEnum.STUDENT)
        {
            new StudentDB(conn).deleteStudent(foundUser.getUsername(),
                    foundUser.getName(),
                    foundUser.getPassword());
        }

        deleteUser.setString(COLUMN_USER_ID_INDEX, id);

        int affectedRows = deleteUser.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't delete user!");
        }

        return foundUser;
    }

    public User updateUser(User newUser) throws SQLException
    {
        User oldUser = queryUserByID(newUser.getUsername());

        updateUser.setString(1, newUser.getPassword());
        updateUser.setString(2, newUser.getName());
        updateUser.setString(3, newUser.getUserType().toString());
        updateUser.setString(4, newUser.getUsername());

        System.out.println(updateUser);

        int affectedRows = updateUser.executeUpdate();

        if (newUser.getUserType() == UserTypesEnum.PROFESSOR)
        {
            new ProfessorDB(conn).updateProfessor((Professor) newUser);
        }
        else if (newUser.getUserType() == UserTypesEnum.STUDENT)
        {
            new StudentDB(conn).updateStudent((Student) newUser);
        }

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't update user!");
        }

        return oldUser;
    }

    public List<User> queryUsers() throws SQLException
    {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = queryUsers.executeQuery())
        {
            while (resultSet.next())
            {
                UserTypesEnum userType = UserTypesEnum.valueOf(resultSet.getString(COLUMN_USER_TYPE_INDEX));

                if (userType == UserTypesEnum.PROFESSOR)
                {
                    users.add(new ProfessorDB(conn).queryProfessor(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX)));

                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    users.add(new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
                else if (userType == UserTypesEnum.STUDENT)
                {
                    users.add(new StudentDB(conn).queryStudent(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
            }

            return users;
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't query users: " + e.getMessage());
        }

        return null;
    }

}