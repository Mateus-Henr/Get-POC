package com.ufv.project.db;

import com.ufv.project.model.Subject;

import java.sql.*;

public class SubjectDB
{

    private static final String TABLE_SUBJECT = "TB_Subject";
    private static final String COLUMN_SUBJECT_ID = "ID";
    private static final String COLUMN_SUBJECT_NAME = "Name";
    private static final String COLUMN_SUBJECT_DESCRIPTION = "Description";

    private static final int COLUMN_SUBJECT_ID_INDEX = 1;
    private static final int COLUMN_SUBJECT_NAME_INDEX = 2;
    private static final int COLUMN_SUBJECT_DESCRIPTION_INDEX = 3;

    private static final String GET_SUBJECT = "SELECT  * FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String GET_SUBJECT_DESCRIPTION = "SELECT " + COLUMN_SUBJECT_DESCRIPTION + " FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String SET_SUBJECT_NAME = "UPDATE " + TABLE_SUBJECT + " SET " + COLUMN_SUBJECT_NAME + " = ? WHERE " + COLUMN_SUBJECT_ID + " = ?";
    private static final String SET_SUBJECT_DESCRIPTION = "UPDATE " + TABLE_SUBJECT + " SET " + COLUMN_SUBJECT_DESCRIPTION + " = ? WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String INSERT_SUBJECT = "INSERT INTO " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID + ", " + COLUMN_SUBJECT_NAME + ", " + COLUMN_SUBJECT_DESCRIPTION + ") VALUES (?, ?, ?)";

    private static final String DELETE_SUBJECT = "DELETE FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String PRINT_SUBJECTS = "SELECT * FROM " + TABLE_SUBJECT;

    private PreparedStatement getSubject;
    private PreparedStatement insertSubject;

    private Connection conn;

    public SubjectDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getSubject = conn.prepareStatement(GET_SUBJECT);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Subject getDisciplineById(int id) throws SQLException
    {
        getSubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

        try (ResultSet resultSet = getSubject.executeQuery())
        {
            if (resultSet.next())
            {
                return new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX),
                        resultSet.getString(COLUMN_SUBJECT_NAME),
                        resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int insertSubject(Subject subjectToInsert) throws SQLException
    {
        insertSubject.setInt(COLUMN_SUBJECT_ID_INDEX, subjectToInsert.getId());
        insertSubject.setString(COLUMN_SUBJECT_NAME_INDEX, subjectToInsert.getName());
        insertSubject.setString(COLUMN_SUBJECT_DESCRIPTION_INDEX, subjectToInsert.getDescription());

        try (ResultSet resultSet = insertSubject.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getInt(COLUMN_SUBJECT_ID_INDEX);
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

        return -1;

    }

    public void dropDiscipline(int id)
    {
        /*
         * This method drops a discipline from the database
         */


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SUBJECT))
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    private static final void printAllDisciplines()
    {
        /*
         * This method prints all disciplines from the database
         */

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(PRINT_SUBJECTS))
        {
            while (rs.next())
            {
                System.out.println(rs.getInt(COLUMN_SUBJECT_ID) + "\t" +
                        rs.getString(COLUMN_SUBJECT_NAME) + "\t" +
                        rs.getString(COLUMN_SUBJECT_DESCRIPTION));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    private static final void printDisciplineById(int id)
    {
        /*
         * This method prints a discipline in the database
         */
        System.out.println("ID: " + id + "\n" +
                "Name: " + getDisciplineName(id) + "\n" +
                "Description: " + getDisciplineDescription(id));
    }

    //verify if keyword exists

}
