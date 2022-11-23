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

    private final Connection conn;

    public SubjectDB(Connection conn)
    {
        this.conn = conn;
    }

    public Subject querySubjectByID(int id) throws SQLException
    {
        try (PreparedStatement querySubject = conn.prepareStatement(QUERY_SUBJECT))
        {
            querySubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

            try (ResultSet resultSet = querySubject.executeQuery())
            {
                if (resultSet.next())
                {
                    return new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX), resultSet.getString(COLUMN_SUBJECT_NAME_INDEX), resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
                }
            }

            return null;
        }
    }

    public List<Subject> querySubjects() throws SQLException
    {
        try (PreparedStatement querySubjects = conn.prepareStatement(QUERY_SUBJECTS);
             ResultSet resultSet = querySubjects.executeQuery())
        {
            List<Subject> subjects = new ArrayList<>();

            while (resultSet.next())
            {
                subjects.add(new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX), resultSet.getString(COLUMN_SUBJECT_NAME_INDEX), resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX)));
            }

            return subjects;
        }
    }

    public int insertSubject(Subject new_subject) throws SQLException
    {
        try (PreparedStatement insertSubject = conn.prepareStatement(INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS))
        {
            insertSubject.setInt(COLUMN_SUBJECT_ID_INDEX, new_subject.getId());
            insertSubject.setString(COLUMN_SUBJECT_NAME_INDEX, new_subject.getName());
            insertSubject.setString(COLUMN_SUBJECT_DESCRIPTION_INDEX, new_subject.getDescription());

            if (insertSubject.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert subject with ID: '" + new_subject.getId() + "'.");
            }

            try (ResultSet generatedKeys = insertSubject.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    return generatedKeys.getInt(1);
                }

                throw new SQLException("ERROR: Couldn't get _id for subject.");
            }
        }
    }

    public Subject deleteSubject(int id) throws SQLException
    {
        Subject foundSubject = querySubjectByID(id);

        if (foundSubject == null)
        {
            throw new SQLException("ERROR: Subject with ID: '" + id + "' doesn't exist.");
        }

        try (PreparedStatement deleteSubject = conn.prepareStatement(DELETE_SUBJECT))
        {
            deleteSubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

            if (deleteSubject.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete subject with ID: '" + id + "'.");
            }

            return foundSubject;
        }
    }

    public Subject updateSubject(Subject newSubject) throws SQLException
    {
        Subject foundSubject = querySubjectByID(newSubject.getId());

        if (foundSubject == null)
        {
            throw new SQLException("ERROR: Subject with ID: '" + newSubject.getId() + "' doesn't exist.");
        }

        try (PreparedStatement updateSubject = conn.prepareStatement(UPDATE_SUBJECT))
        {
            conn.setAutoCommit(false);

            if (newSubject.getName() != null)
            {
                updateSubject.setString(1, newSubject.getName());
            }
            else
            {
                updateSubject.setString(1, foundSubject.getName());
            }

            if (newSubject.getDescription() != null)
            {
                updateSubject.setString(2, newSubject.getDescription());
            }
            else
            {
                updateSubject.setString(2, foundSubject.getDescription());
            }

            updateSubject.setInt(3, newSubject.getId());

            if (updateSubject.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete subject with ID: '" + newSubject.getId() + "'.");
            }

            conn.setAutoCommit(true);

            return foundSubject;
        }
        catch (SQLException e)
        {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

}