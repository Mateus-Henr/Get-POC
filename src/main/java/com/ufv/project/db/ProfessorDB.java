package com.ufv.project.db;

import com.ufv.project.model.Professor;
import com.ufv.project.model.Subject;
import com.ufv.project.model.UserTypesEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProfessorDB
{
    private static final String TABLE_PROFESSOR = "TB_Professor";

    private static final String COLUMN_PROFESSOR_EMAIL = "Email";
    private static final String COLUMN_USER_PROFESSOR_ID = "TB_User_ID";

    private static final int COLUMN_PROFESSOR_EMAIL_INDEX = 1;
    private static final int COLUMN_USER_PROFESSOR_ID_INDEX = 2;

    private static final String QUERY_PROFESSOR = "SELECT * FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";
    private static final String QUERY_PROFESSORS = "SELECT * FROM " + TABLE_PROFESSOR;
    private static final String INSERT_PROFESSOR = "INSERT INTO " + TABLE_PROFESSOR + " (" + COLUMN_PROFESSOR_EMAIL + ", " + COLUMN_USER_PROFESSOR_ID + ") VALUES (?, ?)";
    private static final String UPDATE_PROFESSOR = "UPDATE " + TABLE_PROFESSOR + " SET " + COLUMN_PROFESSOR_EMAIL + " = ? WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";
    private static final String DELETE_PROFESSOR = "DELETE FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";

    private Connection conn;

    private final PreparedStatement queryProfessor;
    private final PreparedStatement queryProfessors;
    private final PreparedStatement insertProfessor;
    private final PreparedStatement updateProfessor;
    private final PreparedStatement deleteProfessor;

    public ProfessorDB(Connection conn) throws SQLException
    {
        this.conn = conn;

        queryProfessor = conn.prepareStatement(QUERY_PROFESSOR);
        queryProfessors = conn.prepareStatement(QUERY_PROFESSORS);
        insertProfessor = conn.prepareStatement(INSERT_PROFESSOR, PreparedStatement.RETURN_GENERATED_KEYS);
        deleteProfessor = conn.prepareStatement(DELETE_PROFESSOR);
        updateProfessor = conn.prepareStatement(UPDATE_PROFESSOR);
    }

    protected Professor queryProfessor(String username, String password, String name) throws SQLException
    {
        Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);
        List<Subject> subjects = professor_has_subjectDB.querySubjectsByProfessor(username);



        queryProfessor.setString(1, username);

        try (ResultSet resultSet = queryProfessor.executeQuery())
        {
            if (resultSet.next())
            {
                return new Professor(username,
                        name,
                        password,
                        resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                        subjects);
            }
        }

        return null;
    }

    private List<Subject> getSubjectsTaughtByProfessorID(String id)
    {
        return null;
    }

    protected String insertProfessor(Professor professor) throws SQLException
    {
        insertProfessor.setString(1, professor.getEmail());
        insertProfessor.setString(2, professor.getUsername());

        Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

        int affectedRows = insertProfessor.executeUpdate();

        for (Subject subject : professor.getSubjectsTaught())
        {
            professor_has_subjectDB.insertProfessorHasSubject(professor.getUsername(), subject.getId());
        }

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't insert professor!");
        }

        return professor.getUsername();
    }

    protected Professor deleteProfessor(String username, String name, String password) throws SQLException
    {
        Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);
        Professor foundProfessor = queryProfessor(username, name, password);

        deleteProfessor.setString(1, username);

        for (Subject subject : foundProfessor.getSubjectsTaught())
        {
            professor_has_subjectDB.deleteProfessorHasSubject(foundProfessor.getUsername(), subject.getId());
        }

        int affectedRows = deleteProfessor.executeUpdate();

        if (affectedRows == 1)
        {
            return foundProfessor;
        }

        return null;
    }

    protected Professor updateProfessor(Professor professor) throws SQLException
    {
        Professor oldProfessor = queryProfessor(professor.getUsername(), professor.getName(), professor.getPassword());

        if (oldProfessor == null)
        {
            return null;
        }

        Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

        //Delete old subjects
        for (Subject subject : oldProfessor.getSubjectsTaught())
        {
            professor_has_subjectDB.deleteProfessorHasSubject(oldProfessor.getUsername(), subject.getId());
        }

        //Insert new subjects
        for (Subject subject : professor.getSubjectsTaught())
        {
            professor_has_subjectDB.insertProfessorHasSubject(professor.getUsername(), subject.getId());
        }

        //Update professor
        if (professor.getEmail() != null)
        {
            updateProfessor.setString(1, professor.getEmail());
        }
        else
        {
            updateProfessor.setString(1, oldProfessor.getEmail());
        }

        updateProfessor.setString(2, professor.getUsername());

        int affectedRows = updateProfessor.executeUpdate();

        if (affectedRows == 1)
        {
            return professor;
        }

        return null;
    }

    public List<Professor> getAllProfessors() throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.PROFESSOR)
                .map(user -> (Professor) user)
                .toList();
    }

    public void close() throws SQLException
    {
        if (queryProfessor != null)
        {
            queryProfessor.close();
        }
        if (queryProfessors != null)
        {
            queryProfessors.close();
        }
        if (insertProfessor != null)
        {
            insertProfessor.close();
        }
        if (updateProfessor != null)
        {
            updateProfessor.close();
        }
        if (deleteProfessor != null)
        {
            deleteProfessor.close();
        }
        if (conn != null)
        {
            conn.close();
        }
    }

}
