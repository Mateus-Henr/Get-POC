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
    private static final String INSERT_USER = "INSERT INTO " + TABLE_USER + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_TYPE + " = ? WHERE " + COLUMN_USER_ID + " = ?";
    private static final String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

    private final Connection conn;

    private final PreparedStatement queryUser;
    private final PreparedStatement queryUsers;
    private final PreparedStatement insertUser;
    private final PreparedStatement updateUser;
    private final PreparedStatement deleteUser;

    public UserDB(Connection conn) throws SQLException
    {
        this.conn = conn;

        queryUser = conn.prepareStatement(QUERY_USER);
        queryUsers = conn.prepareStatement(QUERY_USERS);
        insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        deleteUser = conn.prepareStatement(DELETE_USER);
        updateUser = conn.prepareStatement(UPDATE_USER);
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

        return null;
    }

    public String insertUser(User user) throws SQLException
    {
        // Begin transaction.
        conn.setAutoCommit(false);

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
        String username = null;

        if (userType == UserTypesEnum.PROFESSOR)
        {
            username = new ProfessorDB(conn).insertProfessor((Professor) user);
        }
        else if (userType == UserTypesEnum.STUDENT)
        {
            username = new StudentDB(conn).insertStudent((Student) user);
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            username = user.getUsername();
        }

        // End transaction.
        conn.commit();
        conn.setAutoCommit(true);

        return username;
    }

    public User deleteUserByID(String id) throws SQLException
    {
        User foundUser = queryUserByID(id);

        if (foundUser == null)
        {
            throw new SQLException("Couldn't find the user.");
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

        if (oldUser == null)
        {
            throw new SQLException("User doesn't exist.");
        }

        if (newUser.getPassword() == null)
        {
            updateUser.setString(1, oldUser.getPassword());
        }
        else
        {
            updateUser.setString(1, newUser.getPassword());
        }

        if (newUser.getPassword() == null)
        {
            updateUser.setString(2, oldUser.getPassword());
        }
        else
        {
            updateUser.setString(2, newUser.getPassword());
        }

        if (newUser.getUserType() != oldUser.getUserType())
        {
            updateUser.setString(3, oldUser.getUserType().toString());
        }

        updateUser.setString(4, newUser.getUsername());

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
    }

}