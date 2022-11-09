package com.ufv.project.db;

import java.sql.*;
import java.util.Calendar;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_poc {

    /*
     * TB_POC table columns names
     */
    public static String TABLE_POC = "TB_POC";
    public static String COLUMN_POC_ID = "ID";
    public static String COLUMN_POC_TITLE = "Title";
    public static String COLUMN_POC_DEFENSE_DATE = "Defense_Date";
    public static String COLUMN_POC_SUMMARY = "Summary";
    public static String COLUMN_POC_AREA_ID = "TB_Area_ID";
    public static String COLUMN_POC_PDF_ID = "TB_PDF_ID";
    public static String COLUMN_POC_TEACHER_REGISTRANT_ID = "Teacher_Registrant";
    public static String COLUMN_POC_TEACHER_ADVISOR_ID = "Teacher_Advisor";

    /*
     * Getters and Setters
     *
     */


    public static String getPocTitle(int id) {
        String title = null;
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_TITLE + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
        while (results.next()) {
                title = results.getString(COLUMN_POC_TITLE);
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return title;
    }




    public static void addPoc(int id, String title, Date defense_date, String summary, int area_id, int pdf_id, String teacher_registrant_id, String teacher_advisor_id) {
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_AREA_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES ('" + id + "', '" + title + "', '" + defense_date + "', '" + summary + "', '" + area_id + "', '" + pdf_id + "', '" + teacher_registrant_id + "', '" + teacher_advisor_id + "')");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }

}
