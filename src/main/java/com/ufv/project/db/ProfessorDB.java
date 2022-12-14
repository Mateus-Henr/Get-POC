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
    /* Table Professor constants. */
    private static final String TABLE_PROFESSOR = "TB_Professor";
    private static final String COLUMN_PROFESSOR_EMAIL = "Email";
    private static final String COLUMN_USER_PROFESSOR_ID = "TB_User_ID";

    /* Table Professor constants indexes. */
    private static final int COLUMN_PROFESSOR_EMAIL_INDEX = 1;
    private static final int COLUMN_USER_PROFESSOR_ID_INDEX = 2;

    /* Table Professor queries. */

    private static final String QUERY_PROFESSOR = "SELECT * FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";
    private static final String INSERT_PROFESSOR = "INSERT INTO " + TABLE_PROFESSOR + " (" + COLUMN_PROFESSOR_EMAIL + ", " + COLUMN_USER_PROFESSOR_ID + ") VALUES (?, ?)";
    private static final String UPDATE_PROFESSOR = "UPDATE " + TABLE_PROFESSOR + " SET " + COLUMN_PROFESSOR_EMAIL + " = ? WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";
    private static final String DELETE_PROFESSOR = "DELETE FROM " + TABLE_PROFESSOR + " WHERE " + COLUMN_USER_PROFESSOR_ID + " = ?";

    /* Connection to the database. */
    private final Connection conn;

    public ProfessorDB(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Query a professor by its ID, password and name.
     *
     * @param username ID of the professor to query.
     * @param password password of the professor to query.
     * @param name     name of the professor to query.
     * @return professor with the given ID, password and name.
     * @throws SQLException if an error occurs while querying the database.
     */
    protected Professor queryProfessor(String username, String password, String name) throws SQLException
    {
        try (PreparedStatement queryProfessor = conn.prepareStatement(QUERY_PROFESSOR))
        {
            queryProfessor.setString(1, username);

            Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

            try (ResultSet resultSet = queryProfessor.executeQuery())
            {
                if (resultSet.next())
                {
                    return new Professor(username,
                            name,
                            password,
                            resultSet.getString(COLUMN_PROFESSOR_EMAIL_INDEX),
                            professor_has_subjectDB.querySubjectsByProfessor(username));
                }

                return null;
            }
        }
    }

    /**
     * Insert a professor into the database.
     *
     * @param professor professor to insert.
     * @return inserted professor.
     * @throws SQLException if there is an error while inserting the professor.
     */
    protected String insertProfessor(Professor professor) throws SQLException
    {
        try (PreparedStatement insertProfessor = conn.prepareStatement(INSERT_PROFESSOR))
        {
            insertProfessor.setString(COLUMN_PROFESSOR_EMAIL_INDEX, professor.getEmail());
            insertProfessor.setString(COLUMN_USER_PROFESSOR_ID_INDEX, professor.getUsername());

            if (insertProfessor.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert professor with username: '" + professor.getUsername() + "'.");
            }

            Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

            for (Subject subject : professor.getSubjectsTaught())
            {
                professor_has_subjectDB.insertProfessorHasSubject(professor.getUsername(), subject.getId());
            }

            return professor.getUsername();
        }
    }

    /**
     * Delete a professor from the database.
     *
     * @param username ID of the professor to delete.
     * @param name     name of the professor to delete.
     * @param password password of the professor to delete.
     * @return deleted professor with the given ID, password and name.
     * @throws SQLException if there is an error while deleting the professor.
     */
    protected Professor deleteProfessor(String username, String name, String password) throws SQLException
    {
        Professor foundProfessor = queryProfessor(username, name, password);

        if (foundProfessor == null)
        {
            throw new SQLException("ERROR: Professor with username: '" + username + "' doesn't exists.");
        }

        try (PreparedStatement deleteProfessor = conn.prepareStatement(DELETE_PROFESSOR))
        {
            deleteProfessor.setString(1, username);

            Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

            for (Subject subject : foundProfessor.getSubjectsTaught())
            {
                professor_has_subjectDB.deleteProfessorHasSubject(foundProfessor.getUsername(), subject.getId());
            }

            if (deleteProfessor.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete the professor with username: '" + username + "'.");
            }

            return foundProfessor;
        }
    }

    /**
     * Update a professor in the database.
     *
     * @param newProfessor professor to update.
     * @return updated professor.
     * @throws SQLException if there is an error while updating the professor.
     */
    protected Professor updateProfessor(Professor newProfessor) throws SQLException
    {
        Professor oldProfessor = queryProfessor(newProfessor.getUsername(), newProfessor.getName(), newProfessor.getPassword());

        if (oldProfessor == null)
        {
            throw new SQLException("ERROR: Professor with username: '" + newProfessor.getUsername() + "' doesn't exists.");
        }

        try (PreparedStatement updateProfessor = conn.prepareStatement(UPDATE_PROFESSOR))
        {
            Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(conn);

            // Delete old subjects.
            for (Subject subject : oldProfessor.getSubjectsTaught())
            {
                professor_has_subjectDB.deleteProfessorHasSubject(oldProfessor.getUsername(), subject.getId());
            }

            // Insert new subjects.
            for (Subject subject : newProfessor.getSubjectsTaught())
            {
                professor_has_subjectDB.insertProfessorHasSubject(newProfessor.getUsername(), subject.getId());
            }

            // Update professor.
            if (newProfessor.getEmail() != null)
            {
                updateProfessor.setString(1, newProfessor.getEmail());
            }
            else
            {
                updateProfessor.setString(1, oldProfessor.getEmail());
            }

            updateProfessor.setString(2, newProfessor.getUsername());

            if (updateProfessor.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update professor with username: '" + newProfessor.getUsername() + "'.");
            }

            return newProfessor;
        }
    }

    /**
     * Query all professors from the database.
     *
     * @return list of professors.
     * @throws SQLException if there is an error while querying the database.
     */
    public List<Professor> queryProfessors() throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.PROFESSOR)
                .map(user -> (Professor) user)
                .toList();
    }

}
