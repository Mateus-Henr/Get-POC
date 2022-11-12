package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*public class POCDB
{

      TB_POC table columns names

    private static final String TABLE_POC = "TB_POC";
    private static final String COLUMN_POC_ID = "ID";
    private static final String COLUMN_POC_TITLE = "Title";
    private static final String COLUMN_POC_DEFENSE_DATE = "Defense_Date";
    private static final String COLUMN_POC_SUMMARY = "Summary";
    private static final String COLUMN_POC_FIELD_ID = "TB_Area_ID";
    private static final String COLUMN_POC_PDF_ID = "TB_PDF_ID";
    private static final String COLUMN_POC_TEACHER_REGISTRANT_ID = "Teacher_Registrant";
    private static final String COLUMN_POC_TEACHER_ADVISOR_ID = "Teacher_Advisor";

    private static final int COLUMN_POC_ID_INDEX = 1;
    private static final int COLUMN_POC_TITLE_INDEX = 2;
    private static final int COLUMN_POC_DEFENSE_DATE_INDEX = 3;
    private static final int COLUMN_POC_SUMMARY_INDEX = 4;
    private static final int COLUMN_POC_FIELD_INDEX = 5;
    private static final int COLUMN_POC_PDF_ID_INDEX = 6;
    private static final int COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX = 7;
    private static final int COLUMN_POC_TEACHER_ADVISOR_ID_INDEX = 8;

    private static final String QUERY_POC = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";
    private static final String QUERY_POCs = "SELECT * FROM " + TABLE_POC;
    private static final String INSERT_POC = "INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_AREA_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String DELETE_POC = "DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    private static final String UPDATE_POC = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TITLE + " = ?, " + COLUMN_POC_DEFENSE_DATE + " = ?, " + COLUMN_POC_SUMMARY + " = ?, " + COLUMN_POC_AREA_ID + " = ?, " + COLUMN_POC_PDF_ID + " = ?, " + COLUMN_POC_TEACHER_REGISTRANT_ID + " = ?, " + COLUMN_POC_TEACHER_ADVISOR_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";


    private PreparedStatement queryPOC;
    private PreparedStatement queryPOCs;
    private PreparedStatement insertPOC;
    private PreparedStatement deletePOC;
    private PreparedStatement updatePOC;


    private Connection conn;

    public POCDB(Connection conn){
        this.conn = conn;
        try{
            queryPOC = conn.prepareStatement(QUERY_POC);
            queryPOCs = conn.prepareStatement(QUERY_POCs);
            insertPOC = conn.prepareStatement(INSERT_POC);
            deletePOC = conn.prepareStatement(DELETE_POC);
            updatePOC = conn.prepareStatement(UPDATE_POC);
        }catch(SQLException e){
            System.out.println("Error preparing statements: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int insertPOC(POC poc) throws SQLException
    {
        insertPOC.setInt(COLUMN_POC_ID_INDEX, poc.getId());
        insertPOC.setString(COLUMN_POC_TITLE_INDEX, poc.getTitle());
        insertPOC.setDate(COLUMN_POC_DEFENSE_DATE_INDEX, Date.valueOf(poc.getDefenseDate()));
        insertPOC.setString(COLUMN_POC_SUMMARY_INDEX, poc.getSummary());
        insertPOC.setInt(COLUMN_POC_FIELD_INDEX, poc.getField().getId());
        insertPOC.setInt(COLUMN_POC_PDF_ID_INDEX, poc.getPdf().getId());
        insertPOC.setString(COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX, poc.getRegistrant().getUsername());
        insertPOC.setString(COLUMN_POC_TEACHER_ADVISOR_ID_INDEX, poc.getAdvisor().getUsername());

        int affectedRows = insertPOC.executeUpdate();
        if(affectedRows != 1){
            throw new SQLException("Couldn't insert POC");
        }
        try(ResultSet generatedKeys = insertPOC.getGeneratedKeys()){
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else{
                throw new SQLException("Couldn't get id for POC");
            }
        }
    }
}*/
