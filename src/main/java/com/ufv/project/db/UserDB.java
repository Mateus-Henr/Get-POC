package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
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
    private static final String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    private static final String UPDATE_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_TYPE + " = ? WHERE " + COLUMN_USER_ID + " = ?";

    private Connection conn;

    private PreparedStatement queryUser;
    private PreparedStatement queryUsers;
    private PreparedStatement insertUser;
    private PreparedStatement deleteUser;
    private PreparedStatement updateUser;

    public UserDB(Connection conn) {
        this.conn = conn;

        try {
            queryUser = conn.prepareStatement(QUERY_USER);
            queryUsers = conn.prepareStatement(QUERY_USERS);
            insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            deleteUser = conn.prepareStatement(DELETE_USER);
            updateUser = conn.prepareStatement(UPDATE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public User queryUserByID(String id) throws SQLException
    {
        queryUser.setString(COLUMN_USER_ID_INDEX, id);

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
                    return new StudentDB(conn).getStudentByID(resultSet.getString(COLUMN_USER_ID_INDEX),
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
    }*/

    public String insertUser(User user) throws SQLException {

        insertUser.setString(COLUMN_USER_ID_INDEX, user.getUsername());
        insertUser.setString(COLUMN_USER_PASSWORD_INDEX, user.getPassword());
        insertUser.setString(COLUMN_USER_NAME_INDEX, user.getName());
        insertUser.setString(COLUMN_USER_TYPE_INDEX, user.getUserType().toString());


        int affectedRows = insertUser.executeUpdate();
        if (affectedRows != 1) {
            throw new SQLException("Couldn't insert user!");
        }

        try (ResultSet resultSet = insertUser.getGeneratedKeys())
        {
            UserTypesEnum userType = user.getUserType();

            if (userType == UserTypesEnum.STUDENT){
                System.out.println("Student");
            }
            if (userType == UserTypesEnum.PROFESSOR)
            {
                System.out.println("Professor");
                return new ProfessorDB(conn).insertProfessor((Professor) user);
            }
            else if (userType == UserTypesEnum.STUDENT)
            {
                return new StudentDB(conn).insertStudent((Student) user);
            }
            else if (userType == UserTypesEnum.ADMIN)
            {
                return resultSet.getString(COLUMN_USER_ID_INDEX);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    }


   /* public User deleteUser(String id) throws SQLException
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
                    return new StudentDB(conn).getStudentByID(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    return new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
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
    }*/

    /*public List<User> getAllUsers()
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
                    users.add(new StudentDB(conn).getStudentByID(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
                else if (userType == UserTypesEnum.ADMIN)
                {
                    users.add(new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
            }

            return users;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }*/

