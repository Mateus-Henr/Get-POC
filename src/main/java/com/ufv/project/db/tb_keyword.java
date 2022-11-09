package com.ufv.project.db;

import java.sql.*;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_keyword {

    /*
     * TB_Keyword table names
     */

    public static String TABLE_KEYWORD = "TB_Keyword";
    public static String COLUMN_KEYWORD_ID = "ID";

    public static String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORD + " (" + COLUMN_KEYWORD_ID + ") VALUES (?)";

    public static String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORD_ID + " = ?";

    public static String PRINT_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORD;

    /*
     * Keyword methods
     */

    public static void addKeyword(String id) {
        /*
         * This method adds a keyword to the database
         */


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_KEYWORD)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void dropKeyword(String id) {
        /*
         * This method drops a keyword from the database
         */


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_KEYWORD)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllKeywords() {
        /*
         * This method prints all keywords in the database
         */

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(PRINT_KEYWORDS)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getString(COLUMN_KEYWORD_ID));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printKeywordById(String id) {
        /*
         * This method prints a keyword in the database
         */
        String sql = "SELECT * FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORD_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString(COLUMN_KEYWORD_ID));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    //verify if keyword exists
}
