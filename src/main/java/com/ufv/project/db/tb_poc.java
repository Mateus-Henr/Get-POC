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

    public static String GET_POC_TITLE = "SELECT " + COLUMN_POC_TITLE + " " +
            "FROM " + TABLE_POC + " " +
            "WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_DEFENSE_DATE = "SELECT " + COLUMN_POC_DEFENSE_DATE +
            " FROM " + TABLE_POC +
            " WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_SUMMARY = "SELECT " + COLUMN_POC_SUMMARY +
            " FROM " + TABLE_POC +
            " WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_AREA_ID = "SELECT " + COLUMN_POC_AREA_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_PDF_ID = "SELECT " + COLUMN_POC_PDF_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_TEACHER_REGISTRANT_ID = "SELECT " + COLUMN_POC_TEACHER_REGISTRANT_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    public static String GET_POC_TEACHER_ADVISOR_ID = "SELECT " + COLUMN_POC_TEACHER_ADVISOR_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_TITLE = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TITLE + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_DEFENSE_DATE = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_DEFENSE_DATE + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_SUMMARY = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_SUMMARY + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_AREA_ID = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_AREA_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_PDF_ID = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_PDF_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_TEACHER_REGISTRANT_ID = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TEACHER_REGISTRANT_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";

    public static String SET_POC_TEACHER_ADVISOR_ID = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TEACHER_ADVISOR_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";






    /*
     * Getters and Setters
     *
     */

    public static String getPocTitle(int id) {
        String title = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_TITLE)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            title = rs.getString(COLUMN_POC_TITLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return title;
    }

    public static String getPocDefenseDate(int id) {
        String defenseDate = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_DEFENSE_DATE)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            defenseDate = rs.getString(COLUMN_POC_DEFENSE_DATE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return defenseDate;
    }

    public static String getPocSummary(int id) {
        String summary = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_SUMMARY)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            summary = rs.getString(COLUMN_POC_SUMMARY);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return summary;
    }

    public static int getPocAreaID(int id) {
        int areaID = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_AREA_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            areaID = rs.getInt(COLUMN_POC_AREA_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return areaID;
    }

    public static int getPocPdfID(int id) {
        int pdfID = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_PDF_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            pdfID = rs.getInt(COLUMN_POC_PDF_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pdfID;
    }

    public static String getPocTeacherRegistrant(int id) {
        String teacherRegistrant = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_TEACHER_REGISTRANT_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            teacherRegistrant = rs.getString(COLUMN_POC_TEACHER_REGISTRANT_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teacherRegistrant;
    }

    public static String getPocTeacherAdvisor(int id) {
        String teacherAdvisor = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_POC_TEACHER_ADVISOR_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            teacherAdvisor = rs.getString(COLUMN_POC_TEACHER_ADVISOR_ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teacherAdvisor;
    }

    public static void setPocTitle(int id, String title) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_TITLE)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocDefenseDate(int id, String defenseDate) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_DEFENSE_DATE)) {
            pstmt.setString(1, defenseDate);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocSummary(int id, String summary) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_SUMMARY)) {
            pstmt.setString(1, summary);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocAreaID(int id, int areaID) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_AREA_ID)) {
            pstmt.setInt(1, areaID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocPdfID(int id, int pdfID) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_PDF_ID)) {
            pstmt.setInt(1, pdfID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocTeacherRegistrant(int id, String teacherRegistrant) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_TEACHER_REGISTRANT_ID)) {
            pstmt.setString(1, teacherRegistrant);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPocTeacherAdvisor(int id, String teacherAdvisor) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_POC_TEACHER_ADVISOR_ID)) {
            pstmt.setString(1, teacherAdvisor);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    /*
     * Methods
     */


    public static void addPoc(int id, String title, Date defense_date, String summary, int area_id, int pdf_id, String teacher_registrant_id, String teacher_advisor_id) {
        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_AREA_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES ('" + id + "', '" + title + "', '" + defense_date + "', '" + summary + "', '" + area_id + "', '" + pdf_id + "', '" + teacher_registrant_id + "', '" + teacher_advisor_id + "')");
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }

    public static void dropPoc(int id) {
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void printAllPocs() {
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC);
            while (results.next()) {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void printPoc(int id) {
        System.out.println("ID: " + id +
                "\nTitle: " + getPocTitle(id) +
                "\nDefense Date: " + getPocDefenseDate(id) +
                "\nSummary: " + getPocSummary(id) +
                "\nArea ID: " + getPocAreaID(id) +
                "\nPDF ID: " + getPocPdfID(id) +
                "\nTeacher Registrant ID: " + getPocTeacherRegistrant(id) +
                "\nTeacher Advisor ID: " + getPocTeacherAdvisor(id));
    }

    public static boolean existPOC(int id) {

        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            if (results.next()) {
                return true;
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return false;
    }

    public static void searchPocByTextInTitle(String text) {
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_TITLE + " LIKE '%" + text + "%'");
            while (results.next()) {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void searchPocByTextInSummary(String text) {
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_SUMMARY + " LIKE '%" + text + "%'");
            while (results.next()) {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    //serach poc by authors
    //search poc by


}
