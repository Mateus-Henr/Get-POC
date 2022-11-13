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
    private static final String TABLE_PROFESSOR_CO_ADVISES_POC = "tb_professor_co_advises_poc";
    private static final String COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID = "tb_teacher_tb_user_id";
    private static final String COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID = "tb_poc_id";

    private static final int COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX = 1;
    private static final int COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX = 2;


    private static final String QUERY_PROFESSORS_BY_POC_ID = "SELECT * FROM " + TABLE_PROFESSOR_CO_ADVISES_POC + " WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";
    private static final String UPDATE_PROFESSOR_CO_ADVISES_POC = "UPDATE " + TABLE_PROFESSOR_CO_ADVISES_POC + " SET " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ?, " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ? WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";
    private static final String INSERT_PROFESSOR_CO_ADVISES_POC = "INSERT INTO " + TABLE_PROFESSOR_CO_ADVISES_POC + " (" + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + ", " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + ") VALUES (?, ?)";
    private static final String DELETE_PROFESSOR_CO_ADVISES_POC = "DELETE FROM " + TABLE_PROFESSOR_CO_ADVISES_POC + " WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";

    private Connection conn;

    private final PreparedStatement queryProfessorsByPocId;
    private final PreparedStatement insertProfessor_co_advises_poc;
    private final PreparedStatement updateProfessor_co_advises_poc;
    private final PreparedStatement deleteProfessor_co_advises_poc;

    public Professor_co_advises_pocDB(Connection conn) throws SQLException
    {
        this.conn = conn;

        queryProfessorsByPocId = conn.prepareStatement(QUERY_PROFESSORS_BY_POC_ID);
        insertProfessor_co_advises_poc = conn.prepareStatement(INSERT_PROFESSOR_CO_ADVISES_POC);
        deleteProfessor_co_advises_poc = conn.prepareStatement(DELETE_PROFESSOR_CO_ADVISES_POC);
        updateProfessor_co_advises_poc = conn.prepareStatement(UPDATE_PROFESSOR_CO_ADVISES_POC);
    }

   public List<Professor> queryProfessorsByPocId(int pocId) throws SQLException {
        UserDB userDB = new UserDB(conn);
        List<Professor> professors = new ArrayList<>();
        queryProfessorsByPocId.setInt(1, pocId);
        ResultSet resultSet = queryProfessorsByPocId.executeQuery();
        while (resultSet.next()) {
            professors.add((Professor) userDB.queryUserByID(resultSet.getString(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID)));

        }
        return professors;
    }

    public void insertProfessor_co_advises_poc(String professorID, int pocID) throws SQLException
    {
        insertProfessor_co_advises_poc.setString(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, professorID);
        insertProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX, pocID);

        System.out.println(insertProfessor_co_advises_poc.toString());

        int affectedRows = insertProfessor_co_advises_poc.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't insert professor_co_advises_poc");
        }
    }

    public void deleteProfessor_co_advises_poc(String professor_id, int poc_id) throws SQLException
    {
        deleteProfessor_co_advises_poc.setString(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, professor_id);
        deleteProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX, poc_id);

        int affectedRows = deleteProfessor_co_advises_poc.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't delete professor_co_advises_poc");
        }
    }

    public void updateProfessor_co_advises_poc(String professor_id, int poc_id, String new_professor_id, int new_poc_id) throws SQLException
    {
        updateProfessor_co_advises_poc.setString(1, new_professor_id);
        updateProfessor_co_advises_poc.setInt(2, new_poc_id);
        updateProfessor_co_advises_poc.setString(3, professor_id);
        updateProfessor_co_advises_poc.setInt(4, poc_id);

        int affectedRows = updateProfessor_co_advises_poc.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't update professor_co_advises_poc");
        }
    }


    public void close() throws SQLException
    {
        if (queryProfessorsByPocId != null)
        {
            queryProfessorsByPocId.close();
        }
        if (insertProfessor_co_advises_poc != null)
        {
            insertProfessor_co_advises_poc.close();
        }
        if (deleteProfessor_co_advises_poc != null)
        {
            deleteProfessor_co_advises_poc.close();
        }
        if (updateProfessor_co_advises_poc != null)
        {
            updateProfessor_co_advises_poc.close();
        }
    }

}
