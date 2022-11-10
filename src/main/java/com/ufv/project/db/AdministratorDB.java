package com.ufv.project.db;

import com.ufv.project.model.Administrator;
import com.ufv.project.model.Subject;
import com.ufv.project.model.User;
import com.ufv.project.model.UserTypesEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdministratorDB
{
    private static final String TABLE_ADMINISTRATOR = "TB_Administrator";

    private static final String COLUMN_ADMINISTRATOR_REGISTRATION = "Registration";
    private static final String COLUMN_ADMINISTRATOR_EMAIL = "Email";
    private static final String COLUMN_USER_ADMINISTRATOR_ID = "TB_User_ID";

    private static final int COLUMN_ADMINISTRATOR_REGISTRATION_INDEX = 1;
    private static final int COLUMN_ADMINISTRATOR_EMAIL_INDEX = 2;
    private static final int COLUMN_USER_ADMINISTRATOR_ID_INDEX = 3;

    private static final String GET_ADMINISTRATOR = "SELECT * FROM " + TABLE_ADMINISTRATOR + " WHERE " + COLUMN_USER_ADMINISTRATOR_ID + " = ?";

    private static final String INSERT_ADMINISTRATOR = "INSERT INTO " +
            TABLE_ADMINISTRATOR + " (" + COLUMN_USER_ADMINISTRATOR_ID + ") VALUES (?)";

    private static final String DELETE_ADMINISTRATOR = "DELETE FROM " + TABLE_ADMINISTRATOR + " WHERE " + COLUMN_USER_ADMINISTRATOR_ID + " = ?";

    private PreparedStatement getAdministrator;
    private PreparedStatement insertAdministrator;
    private PreparedStatement updateAdministrator;
    private PreparedStatement deleteAdministrator;

    private Connection conn;

    public AdministratorDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getAdministrator = conn.prepareStatement(GET_ADMINISTRATOR);
            insertAdministrator = conn.prepareStatement(INSERT_ADMINISTRATOR);
            deleteAdministrator = conn.prepareStatement(DELETE_ADMINISTRATOR);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public User getAdministratorByID(String username, String name, String password) throws SQLException
    {
        getAdministrator.setString(1, username);

        try (ResultSet resultSet = getAdministrator.executeQuery())
        {
            if (resultSet.next())
            {
                return new Administrator(username,
                        name,
                        password);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Subject> getSubjectsTaughtByAdministratorID(String id)
    {
        return null;
    }

    public String insertAdministrator(Administrator administrator) throws SQLException
    {
        insertAdministrator.setString(COLUMN_USER_ADMINISTRATOR_ID_INDEX, administrator.getUsername());

        try (ResultSet resultSet = insertAdministrator.executeQuery())
        {
            if (resultSet.next())
            {
                return administrator.getUsername();
            }
            else
            {
                System.out.println("Error when inserting area.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Administrator deleteAdministrator(String username, String name, String password) throws SQLException
    {
        deleteAdministrator.setString(1, username);

        try (ResultSet resultSet = deleteAdministrator.executeQuery())
        {
            if (resultSet.next())
            {
                return new Administrator(username,
                        name,
                        password);
            }
            else
            {
                System.out.println("Error when deleting discipline.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Administrator> getAllAdministrators()
    {
        return new UserDB(conn).getAllUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.ADMIN)
                .map(user -> (Administrator) user)
                .toList();
    }

}
