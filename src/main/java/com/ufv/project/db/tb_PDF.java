package com.ufv.project.db;

import java.sql.*;
import java.util.Calendar;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_PDF {

    /*
     * TB_PDF table columns names
     */

    public static final String TABLE_PDF = "TB_PDF";
    public static final String COLUMN_PDF_ID = "ID";
    public static final String COLUMN_PDF_CREATION_DATE = "Creation_Date";
    public static final String COLUMN_PDF_CONTENT = "Content";

    /*
     * Getters and Setters
     */

    public static String getPDFContent(int id) {
        /*
         * This method returns the content of a PDF
         */
        String sql = "SELECT " + COLUMN_PDF_CONTENT + " FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(COLUMN_PDF_CONTENT);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;

    }

    public static String getPDFCreationDate(int id) {
        /*
         * This method returns the creation date of a PDF
         */
        String sql = "SELECT " + COLUMN_PDF_CREATION_DATE + " FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(COLUMN_PDF_CREATION_DATE);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;

    }

    public static void setPDFContent(int id, String content) {
        /*
         * This method sets the content of a PDF
         */
        String sql = "UPDATE " + TABLE_PDF + " SET " + COLUMN_PDF_CONTENT + " = ? WHERE " + COLUMN_PDF_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void setPDFCreationDate(int id, Date date) {
        /*
         * This method sets the creation date of a PDF
         */
        String sql = "UPDATE " + TABLE_PDF + " SET " + COLUMN_PDF_CREATION_DATE + " = ? WHERE " + COLUMN_PDF_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }


    /*
     * Methods
     */
    public static Date setDate(int year, int month, int day) {
        /*
         * This method sets a date
         */
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return new Date(cal.getTimeInMillis());
    }

    public static void addPDF(int id, Date date, String content) {
        /*
         * This method adds a pdf to the database
         */

        String sql = "INSERT INTO " +
                TABLE_PDF + " (" + COLUMN_PDF_ID + ", " +
                COLUMN_PDF_CREATION_DATE + ", " + COLUMN_PDF_CONTENT + ") VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setDate(2, date);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void dropPDF(int id) {
        /*
         * This method drops a pdf from the database
         */

        String sql = "DELETE FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllPDFs() {
        /*
         * This method prints all the pdfs from the database
         */

        String sql = "SELECT * FROM " + TABLE_PDF;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt(COLUMN_PDF_ID) + "\t" +
                        rs.getDate(COLUMN_PDF_CREATION_DATE) + "\t" +
                        rs.getString(COLUMN_PDF_CONTENT));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printPDFById(int id) {
        System.out.println("ID: " + id + "\n" +
                "Creation date: " + getPDFCreationDate(id) + "\n" +
                "Content: " + getPDFContent(id));
    }
}
