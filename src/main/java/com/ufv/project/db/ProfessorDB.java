package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.List;

public class ProfessorDB
{
    private static final String TABLE_PROFESSOR = "TB_Teacher";
    private static final String COLUMN_PROFESSOR_EMAIL = "Email";
    private static final String COLUMN_USER_PROFESSOR_ID = "TB_User_ID";


    private static final int COLUMN_PROFESSOR_EMAIL_INDEX = 1;
    private static final int COLUMN_USER_PROFESSOR_ID_INDEX = 2;

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

    public User getProfessorByUser(User user) throws SQLException
    {
        String id = user.getUsername();

        getProfessor.setString(COLUMN_PROFESSOR_ID_INDEX, id);

        try (ResultSet resultSet = getProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return new Professor(id,
                        user.getName(),
                        user.getPassword(),
                        resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                        getSubjectsTaughtByProfessorID(id));
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


    protected String insertProfessor(Professor professor) throws SQLException
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

    protected void deleteProfessor(String id) throws SQLException
    {
        deleteProfessor.setString(1, id);

        try (ResultSet resultSet = deleteProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return new Professor(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX),
                        resultSet.getString(COLUMN_SUBJECT_NAME),
                        resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
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

    public void getAllTeachers()
    {
        Stri

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                System.out.println(rs.getString(COLUMN_USER_ID) + "\t" +
                        rs.getString(COLUMN_USER_PASSWORD) + "\t" +
                        rs.getString(COLUMN_USER_NAME) + "\t" +
                        rs.getString(COLUMN_USER_TYPE) + "\t" +
                        rs.getString(COLUMN_PROFESSOR_EMAIL));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

    }


}
