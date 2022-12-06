package com.ufv.project.db;

import com.ufv.project.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Professor_co_advises_pocDB
{
    /* Table Professor_co_advises_poc constants. */

    private static final String TABLE_PROFESSOR_CO_ADVISES_POC = "tb_professor_co_advises_poc";
    private static final String COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID = "tb_teacher_tb_user_id";
    private static final String COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID = "tb_poc_id";

    /* Table Professor_co_advises_poc constants indexes. */

    private static final int COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX = 1;
    private static final int COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX = 2;

    /* Table Professor_co_advises_poc queries. */
    private static final String QUERY_PROFESSORS_BY_POC_ID = "SELECT * FROM " + TABLE_PROFESSOR_CO_ADVISES_POC + " WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";
    private static final String UPDATE_PROFESSOR_CO_ADVISES_POC = "UPDATE " + TABLE_PROFESSOR_CO_ADVISES_POC + " SET " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ?, " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ? WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";
    private static final String INSERT_PROFESSOR_CO_ADVISES_POC = "INSERT INTO " + TABLE_PROFESSOR_CO_ADVISES_POC + " (" + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + ", " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + ") VALUES (?, ?)";
    private static final String DELETE_PROFESSOR_CO_ADVISES_POC = "DELETE FROM " + TABLE_PROFESSOR_CO_ADVISES_POC + " WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";

    /* Connection to the database. */

    private final Connection conn;

    public Professor_co_advises_pocDB(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Query professors by POC id.
     *
     * @param pocId POC id to query.
     * @return List of professors that co-advises the POC.
     * @throws SQLException If an error occurs while querying the database.
     */

    public List<Professor> queryProfessorsByPocId(int pocId) throws SQLException
    {
        try (PreparedStatement queryProfessorsByPocId = conn.prepareStatement(QUERY_PROFESSORS_BY_POC_ID))
        {
            queryProfessorsByPocId.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, pocId);

            try (ResultSet resultSet = queryProfessorsByPocId.executeQuery())
            {
                List<Professor> professors = new ArrayList<>();
                UserDB userDB = new UserDB(conn);

                while (resultSet.next())
                {
                    professors.add((Professor) userDB.queryUserByID(resultSet.getString(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID)));
                }

                return professors;
            }
        }
    }

    /**
     * Insert a new professor co-advises POC relation.
     *
     * @param professorID Professor id to insert in the relation.
     * @param POCID POC id to insert in the relation.
     * @throws SQLException If an error occurs while inserting the relation.
     */

    public void insertProfessor_co_advises_poc(String professorID, int POCID) throws SQLException
    {
        try (PreparedStatement insertProfessor_co_advises_poc = conn.prepareStatement(INSERT_PROFESSOR_CO_ADVISES_POC))
        {
            insertProfessor_co_advises_poc.setString(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, professorID);
            insertProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX, POCID);

            if (insertProfessor_co_advises_poc.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert professor_co_advises_poc with professor ID: '" + professorID + "' and pocID = '" + POCID + "'.");
            }
        }
    }

    /**
     * Delete a professor co-advises POC relation.
     *
     * @param POCID POC id to delete the relation.
     * @throws SQLException If an error occurs while deleting the relation.
     */

    public void deleteProfessor_co_advises_poc(int POCID) throws SQLException
    {
        try (PreparedStatement deleteProfessor_co_advises_poc = conn.prepareStatement(DELETE_PROFESSOR_CO_ADVISES_POC))
        {
            deleteProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, POCID);

            if (deleteProfessor_co_advises_poc.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete professor_co_advises_poc with POC ID: '" + POCID + "'.");
            }
        }
    }

    /**
     * Update a professor co-advises POC relation.
     *
     * @param oldProfessorID old professor id to update the relation.
     * @param oldPOCID old POC id to update the relation.
     * @param newProfessorID new professor id to update the relation.
     * @param newPOCID new POC id to update the relation.
     * @throws SQLException If an error occurs while updating the relation.
     */

    public void updateProfessor_co_advises_poc(String oldProfessorID, int oldPOCID, String newProfessorID, int newPOCID) throws SQLException
    {
        try (PreparedStatement updateProfessor_co_advises_poc = conn.prepareStatement(UPDATE_PROFESSOR_CO_ADVISES_POC))
        {
            updateProfessor_co_advises_poc.setString(1, newProfessorID);
            updateProfessor_co_advises_poc.setInt(2, newPOCID);
            updateProfessor_co_advises_poc.setString(3, oldProfessorID);
            updateProfessor_co_advises_poc.setInt(4, oldPOCID);

            if (updateProfessor_co_advises_poc.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update professor_co_advises_poc with professor ID: '" + oldProfessorID + "' and POC ID: '" + oldPOCID + "'.");
            }
        }
    }

}
