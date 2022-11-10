package com.ufv.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Professor_has_fieldDB {

    public static final String TABLE_PROFESSOR_HAS_FIELD = "tb_teacher_has_discipline";
    public static final String COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID = "TB_teacher_User_id";
    public static final String COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID = "TB_Discipline_id";

    public static final int COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID_INDEX = 1;
    public static final int COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID_INDEX = 2;

    public static final String INSERT_PROFESSOR_HAS_FIELD = "INSERT INTO " + TABLE_PROFESSOR_HAS_FIELD + " (" + COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID + ", " + COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID + ") VALUES (?, ?)";
    public static final String DELETE_PROFESSOR_HAS_FIELD = "DELETE FROM " + TABLE_PROFESSOR_HAS_FIELD + " WHERE " + COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID + " = ?";
    public static final String GET_ALL_PROFESSOR_HAS_FIELD = "SELECT * FROM " + TABLE_PROFESSOR_HAS_FIELD;
    public static final String UPDATE_PROFESSOR_HAS_FIELD = "UPDATE " + TABLE_PROFESSOR_HAS_FIELD + " SET " + COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID + " = ?, " + COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID + " = ? WHERE " + COLUMN_PROFESSOR_HAS_FIELD_PROFESSOR_ID + " = ? AND " + COLUMN_PROFESSOR_HAS_FIELD_FIELD_ID + " = ?";

    private PreparedStatement insertProfessorHasField;
    private PreparedStatement deleteProfessorHasField;
    private PreparedStatement getProfessorHasField;
    private PreparedStatement updateProfessorHasField;

    private final Connection conn;

    public Professor_has_fieldDB(Connection conn) {
        this.conn = conn;
        try {
            insertProfessorHasField = conn.prepareStatement(INSERT_PROFESSOR_HAS_FIELD);
            deleteProfessorHasField = conn.prepareStatement(DELETE_PROFESSOR_HAS_FIELD);
            getProfessorHasField = conn.prepareStatement(GET_ALL_PROFESSOR_HAS_FIELD);
            updateProfessorHasField = conn.prepareStatement(UPDATE_PROFESSOR_HAS_FIELD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // insert e +





}
