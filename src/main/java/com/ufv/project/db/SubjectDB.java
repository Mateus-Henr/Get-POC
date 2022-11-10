package com.ufv.project.db;

import com.ufv.project.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDB {

    private static final String TABLE_SUBJECT = "TB_Subject";
    private static final String COLUMN_SUBJECT_ID = "ID";
    private static final String COLUMN_SUBJECT_NAME = "Name";
    private static final String COLUMN_SUBJECT_DESCRIPTION = "Description";

    private static final int COLUMN_SUBJECT_ID_INDEX = 1;
    private static final int COLUMN_SUBJECT_NAME_INDEX = 2;
    private static final int COLUMN_SUBJECT_DESCRIPTION_INDEX = 3;

    private static final String SET_SUBJECT_NAME = "UPDATE " + TABLE_SUBJECT + " SET " + COLUMN_SUBJECT_NAME + " = ? WHERE " + COLUMN_SUBJECT_ID + " = ?";

    private static final String SET_SUBJECT_DESCRIPTION = "UPDATE " + TABLE_SUBJECT + " SET " + COLUMN_SUBJECT_DESCRIPTION + " = ? WHERE " + COLUMN_SUBJECT_ID + " = ?";
    private static final String INSERT_SUBJECT = "INSERT INTO " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID + ", " + COLUMN_SUBJECT_NAME + ", " + COLUMN_SUBJECT_DESCRIPTION + ") VALUES (?, ?, ?)";
    private static final String DELETE_SUBJECT = "DELETE FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = ?";
    private static final String GET_ALL_SUBJECTS = "SELECT * FROM " + TABLE_SUBJECT;

    private PreparedStatement getSubject;
    private PreparedStatement insertSubject;

    private PreparedStatement updateSubject;
    private PreparedStatement deleteSubject;

    private Connection conn;

    public SubjectDB(Connection conn) {
        this.conn = conn;

        try {
            insertSubject = conn.prepareStatement(INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
           //updateSubject = conn.prepareStatement(SET_SUBJECT_NAME);
            deleteSubject = conn.prepareStatement(DELETE_SUBJECT);
            getSubject = conn.prepareStatement(GET_ALL_SUBJECTS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Subject getSubjectById(int id) throws SQLException {
        getSubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);

        try (ResultSet resultSet = getSubject.executeQuery()) {
            if (resultSet.next()) {
                return new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX),
                        resultSet.getString(COLUMN_SUBJECT_NAME),
                        resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int insertSubject(Subject subjectToInsert) throws SQLException {
        insertSubject.setInt(COLUMN_SUBJECT_ID_INDEX, subjectToInsert.getId());
        insertSubject.setString(COLUMN_SUBJECT_NAME_INDEX, subjectToInsert.getName());
        insertSubject.setString(COLUMN_SUBJECT_DESCRIPTION_INDEX, subjectToInsert.getDescription());

        try (ResultSet resultSet = insertSubject.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_SUBJECT_ID_INDEX);
            } else {
                System.out.println("Error when inserting area.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    public Subject deleteSubject(int id) throws SQLException {
        deleteSubject.setInt(COLUMN_SUBJECT_ID_INDEX, id);
        try (ResultSet resultSet = deleteSubject.executeQuery()) {
            if (resultSet.next()) {
                return new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX),
                        resultSet.getString(COLUMN_SUBJECT_NAME),
                        resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX));
            } else {
                System.out.println("Error when deleting discipline.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    private List<Subject> getAllSubjects() {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SUBJECTS)) {
            List<Subject> subjects = new ArrayList<>();

            while (resultSet.next()) {
                subjects.add(new Subject(resultSet.getInt(COLUMN_SUBJECT_ID_INDEX),
                        resultSet.getString(COLUMN_SUBJECT_NAME),
                        resultSet.getString(COLUMN_SUBJECT_DESCRIPTION_INDEX)));
            }

            return subjects;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }
}

