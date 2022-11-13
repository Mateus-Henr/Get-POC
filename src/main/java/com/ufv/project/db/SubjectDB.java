package com.ufv.project.db;

import com.ufv.project.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDB
{
    private static final String TABLE_SUBJECT = "TB_Subject";
    private static final String COLUMN_SUBJECT_ID = "ID";
    private static final String COLUMN_SUBJECT_NAME = "Name";
    private static final String COLUMN_SUBJECT_DESCRIPTION = "Description";

    private static final int COLUMN_SUBJECT_ID_INDEX = 1;
    private static final int COLUMN_SUBJECT_NAME_INDEX = 2;
    private static final int COLUMN_SUBJECT_DESCRIPTION_INDEX = 3;

    private static final String QUERY_SUBJECT = "SELECT * FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String QUERY_SUBJECTS = "SELECT * FROM " + TABLE_SUBJECT;
    private static final String INSERT_SUBJECT = "INSERT INTO " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID + ", " + COLUMN_SUBJECT_NAME + ", " + COLUMN_SUBJECT_DESCRIPTION + ") VALUES (?, ?, ?)";
    private static final String UPDATE_SUBJECT = "UPDATE " + TABLE_SUBJECT + " SET " + COLUMN_SUBJECT_NAME + " = ?, " + COLUMN_SUBJECT_DESCRIPTION + " = ? WHERE " + COLUMN_SUBJECT_ID + " = ?";
    private static final String DELETE_SUBJECT = "DELETE FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private PreparedStatement querySubject;
    private PreparedStatement querySubjects;
    private PreparedStatement insertSubject;
    private PreparedStatement updateSubject;
    private PreparedStatement deleteSubject;

    private Connection conn;

    public SubjectDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            querySubject = conn.prepareStatement(QUERY_SUBJECT);
            querySubjects = conn.prepareStatement(QUERY_SUBJECTS);
            insertSubject = conn.prepareStatement(INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
            updateSubject = conn.prepareStatement(UPDATE_SUBJECT);
            deleteSubject = conn.prepareStatement(DELETE_SUBJECT);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Subject querySubjectByID(int id) throws SQLException
    {
        querySubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

        try (ResultSet resultSet = querySubject.executeQuery())
        {
            if (resultSet.next())
            {
                return new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX), resultSet.getString(COLUMN_SUBJECT_NAME_INDEX), resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Subject> querySubjects() throws SQLException
    {
        List<Subject> subjects = new ArrayList<>();

        try (ResultSet resultSet = querySubjects.executeQuery())
        {
            while (resultSet.next())
            {
                subjects.add(new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX), resultSet.getString(COLUMN_SUBJECT_NAME_INDEX), resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX)));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return subjects;
    }

    public int insertSubject(Subject new_subject) throws SQLException
    {
        insertSubject.setInt(COLUMN_SUBJECT_ID_INDEX, new_subject.getId());
        insertSubject.setString(COLUMN_SUBJECT_NAME_INDEX, new_subject.getName());
        insertSubject.setString(COLUMN_SUBJECT_DESCRIPTION_INDEX, new_subject.getDescription());

        int affectedRows = insertSubject.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't insert subject");
        }

        try (ResultSet generatedKeys = insertSubject.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                return generatedKeys.getInt(1);
            }
            else
            {
                throw new SQLException("Couldn't get id for subject");
            }
        }
    }

    public Subject deleteSubject(int id) throws SQLException
    {
        Subject subject = querySubjectByID(id);
        deleteSubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

        int affectedRows = deleteSubject.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't delete subject");
        }

        return subject;
    }

    public Subject updateSubject(Subject new_subject) throws SQLException
    {
        Subject old = querySubjectByID(new_subject.getId());

        if (old == null)
        {
            return null;
        }

        if (new_subject.getName() != null)
        {
            updateSubject.setString(1, new_subject.getName());
        }

        else
        {
            updateSubject.setString(1, old.getName());
        }

        if (new_subject.getDescription() != null)
        {
            updateSubject.setString(2, new_subject.getDescription());
        }

        else
        {
            updateSubject.setString(2, old.getDescription());
        }

        updateSubject.setInt(3, new_subject.getId());

        int affectedRows = updateSubject.executeUpdate();

        if (affectedRows == 1)
        {
            return old;
        }

        return null;
    }

    public void close()
    {
        try
        {
            if (querySubject != null)
            {
                querySubject.close();
            }
            if (querySubjects != null)
            {
                querySubjects.close();
            }
            if (insertSubject != null)
            {
                insertSubject.close();
            }
            if (updateSubject != null)
            {
                updateSubject.close();
            }
            if (deleteSubject != null)
            {
                deleteSubject.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

}