package com.ufv.project.db;

import java.sql.*;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_discipline {

    /*
     *  TB_Discipline table columns names
     */
    public static String TABLE_DISCIPLINE = "TB_Discipline";
    public static String COLUMN_DISCIPLINE_ID = "ID";
    public static String COLUMN_DISCIPLINE_NAME = "Name";
    public static String COLUMN_DISCIPLINE_DESCRIPTION = "Description";

    /*
     * Getters and Setters
     */
    public static String getDisciplineName(int id) {
        /*
         * This method returns the name of an discipline
         */
        String sql = "SELECT " + COLUMN_DISCIPLINE_NAME + " FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(COLUMN_DISCIPLINE_NAME);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }

    public static String getDisciplineDescription(int id) {
        /*
         * This method returns the description of an discipline
         */
        String sql = "SELECT " + COLUMN_DISCIPLINE_DESCRIPTION + " FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(COLUMN_DISCIPLINE_DESCRIPTION);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }

    public static void setDisciplineName(int id, String name) {
        /*
         * This method updates a discipline name in the database
         */
        String sql = "UPDATE " + TABLE_DISCIPLINE + " SET " + COLUMN_DISCIPLINE_NAME + " = ? WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void setDisciplineDescription(int id, String description) {
        /*
         * This method updates a discipline description in the database
         */
        String sql = "UPDATE " + TABLE_DISCIPLINE + " SET " + COLUMN_DISCIPLINE_DESCRIPTION + " = ? WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    /*
    * Methods
     */


    public static void addDiscipline(int id, String name, String description) {
        /*
         * This method adds a discipline to the database
         */

        String sql = "INSERT INTO " +
                TABLE_DISCIPLINE + " (" + COLUMN_DISCIPLINE_ID + ", " +
                COLUMN_DISCIPLINE_NAME + ", " + COLUMN_DISCIPLINE_DESCRIPTION + ") VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

    }

    public static void dropDiscipline(int id) {
        /*
         * This method drops a discipline from the database
         */

        String sql = "DELETE FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllDisciplines() {
        /*
         * This method prints all disciplines from the database
         */

        String sql = "SELECT * FROM " + TABLE_DISCIPLINE;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt(COLUMN_DISCIPLINE_ID) + "\t" +
                        rs.getString(COLUMN_DISCIPLINE_NAME) + "\t" +
                        rs.getString(COLUMN_DISCIPLINE_DESCRIPTION));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printDisciplineById(int id) {
        /*
         * This method prints a discipline in the database
         */
        System.out.println("ID: " + id + "\n" +
                "Name: " + getDisciplineName(id) + "\n" +
                "Description: " + getDisciplineDescription(id));
    }


}
