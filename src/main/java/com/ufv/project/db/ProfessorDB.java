package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDB
{
    private static final String TABLE_PROFESSOR = "TB_Teacher";
    private static final String COLUMN_PROFESSOR_EMAIL = "Email";
    private static final String COLUMN_USER_PROFESSOR_ID = "TB_User_ID";


    private static final int COLUMN_PROFESSOR_EMAIL_INDEX = 1;
    private static final int COLUMN_USER_PROFESSOR_ID_INDEX = 2;

    private static final String GET_ALL_PROFESSORS = "SELECT * FROM " + TABLE_PROFESSOR;

    private static final String GET_PROFESSOR = "SELECT * FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";

    private static final String INSERT_PROFESSOR = "INSERT INTO " +
            TABLE_PROFESSOR + " (" + COLUMN_PROFESSOR_EMAIL + ", " +
            COLUMN_USER_PROFESSOR_ID + ") VALUES (?, ?)";

    private static final String DELETE_PROFESSOR = "DELETE FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";

    private PreparedStatement getProfessor;
    private PreparedStatement insertProfessor;
    private PreparedStatement updateProfessor;
    private PreparedStatement deleteProfessor;

    private Connection conn;

    public ProfessorDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getProfessor = conn.prepareStatement(GET_PROFESSOR);
            insertProfessor = conn.prepareStatement(INSERT_PROFESSOR);
            deleteProfessor = conn.prepareStatement(DELETE_PROFESSOR);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public User getProfessorByID(String username, String name, String password) throws SQLException
    {
        getProfessor.setString(1, username);

        try (ResultSet resultSet = getProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return new Professor(username,
                        name,
                        password,
                        resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                        getSubjectsTaughtByProfessorID(username));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Subject> getSubjectsTaughtByProfessorID(String id)
    {
        return null;
    }


    public String insertProfessor(Professor professor) throws SQLException
    {
        insertProfessor.setString(COLUMN_PROFESSOR_EMAIL_INDEX, professor.getEmail());
        insertProfessor.setString(COLUMN_USER_PROFESSOR_ID_INDEX, professor.getUsername());

        try (ResultSet resultSet = insertProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return professor.getUsername();
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

    public Professor deleteProfessor(String username, String name, String password) throws SQLException
    {
        deleteProfessor.setString(1, username);

        try (ResultSet resultSet = deleteProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return new Professor(username,
                        name,
                        password,
                        resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                        getSubjectsTaughtByProfessorID(username));
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

    public List<Professor> getAllProfessors()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PROFESSORS))
        {
            List<Professor> professors = new ArrayList<>();

            while (resultSet.next())
            {
                professors.add(new Professor(resultSet.));
            }

            return professors;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }


}
