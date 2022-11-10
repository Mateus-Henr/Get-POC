package com.ufv.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Professor_co_advises_pocDB {

    public static final String TABLE_PROFESSOR_CO_ADVISES_POC = "tb_teacher_co_advises_poc";
    public static final String COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID = "tb_teacher_tb_user_id";
    public static final String COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID = "tb_poc_id";

    public static final int COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX = 1;
    public static final int COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX = 2;

    public static final String INSERT_PROFESSOR_CO_ADVISES_POC = "INSERT INTO " + TABLE_PROFESSOR_CO_ADVISES_POC + " (" + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + ", " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + ") VALUES (?, ?)";
    public static final String DELETE_PROFESSOR_CO_ADVISES_POC = "DELETE FROM " + TABLE_PROFESSOR_CO_ADVISES_POC + " WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";
    public static final String GET_ALL_PROFESSOR_CO_ADVISES_POC = "SELECT * FROM " + TABLE_PROFESSOR_CO_ADVISES_POC;
    public static final String UPDATE_PROFESSOR_CO_ADVISES_POC = "UPDATE " + TABLE_PROFESSOR_CO_ADVISES_POC + " SET " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ?, " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ? WHERE " + COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID + " = ?";

    private PreparedStatement insertProfessor_co_advises_poc;
    private PreparedStatement deleteProfessor_co_advises_poc;
    private PreparedStatement getProfessor_co_advises_poc;
    private PreparedStatement updateProfessor_co_advises_poc;

    private final Connection conn;

    public Professor_co_advises_pocDB(Connection conn) {
        this.conn = conn;

        try {
            insertProfessor_co_advises_poc = conn.prepareStatement(INSERT_PROFESSOR_CO_ADVISES_POC);
            deleteProfessor_co_advises_poc = conn.prepareStatement(DELETE_PROFESSOR_CO_ADVISES_POC);
            getProfessor_co_advises_poc = conn.prepareStatement(GET_ALL_PROFESSOR_CO_ADVISES_POC);
            updateProfessor_co_advises_poc = conn.prepareStatement(UPDATE_PROFESSOR_CO_ADVISES_POC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* public static void insertProfessor_co_advises_poc(int professor_id, int poc_id) {
        try {
            insertProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_PROFESSOR_ID_INDEX, professor_id);
            insertProfessor_co_advises_poc.setInt(COLUMN_PROFESSOR_CO_ADVISES_POC_POC_ID_INDEX, poc_id);
            insertProfessor_co_advises_poc.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/




}
