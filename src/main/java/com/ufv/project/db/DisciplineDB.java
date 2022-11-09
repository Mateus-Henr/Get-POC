package com.ufv.project.db;

import java.sql.*;

import static com.ufv.project.db.ConnectDB.connect;

public class DisciplineDB {

    public static String TABLE_DISCIPLINE = "TB_Discipline";
    public static String COLUMN_DISCIPLINE_ID = "ID";
    public static String COLUMN_DISCIPLINE_NAME = "Name";
    public static String COLUMN_DISCIPLINE_DESCRIPTION = "Description";

    public static String GET_DISCIPLINE_NAME = "SELECT " + COLUMN_DISCIPLINE_NAME + " FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";

    public static String GET_DISCIPLINE_DESCRIPTION = "SELECT " + COLUMN_DISCIPLINE_DESCRIPTION + " FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";

    public static String SET_DISCIPLINE_NAME = "UPDATE " + TABLE_DISCIPLINE + " SET " + COLUMN_DISCIPLINE_NAME + " = ? WHERE " + COLUMN_DISCIPLINE_ID + " = ?";
    public static String SET_DISCIPLINE_DESCRIPTION = "UPDATE " + TABLE_DISCIPLINE + " SET " + COLUMN_DISCIPLINE_DESCRIPTION + " = ? WHERE " + COLUMN_DISCIPLINE_ID + " = ?";

    public static String INSERT_DISCIPLINE = "INSERT INTO " + TABLE_DISCIPLINE + " (" + COLUMN_DISCIPLINE_ID + ", " + COLUMN_DISCIPLINE_NAME + ", " + COLUMN_DISCIPLINE_DESCRIPTION + ") VALUES (?, ?, ?)";

    public static String DELETE_DISCIPLINE = "DELETE FROM " + TABLE_DISCIPLINE + " WHERE " + COLUMN_DISCIPLINE_ID + " = ?";

    public static String PRINT_DISCIPLINES = "SELECT * FROM " + TABLE_DISCIPLINE;


    /*
     * Getters and Setters
     */
    public static String getDisciplineName(int id) {
        /*
         * This method returns the name of a discipline
         */
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_DISCIPLINE_NAME)) {
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
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(GET_DISCIPLINE_DESCRIPTION)) {
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

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_DISCIPLINE_NAME)) {
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

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SET_DISCIPLINE_DESCRIPTION)) {
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


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_DISCIPLINE)) {
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


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_DISCIPLINE)) {
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

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(PRINT_DISCIPLINES)) {
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

    //verify if keyword exists

}
