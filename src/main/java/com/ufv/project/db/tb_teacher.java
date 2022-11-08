package com.ufv.project.db;

import java.sql.*;

import static com.ufv.project.db.ConnectDB.connect;
import static com.ufv.project.db.tb_user.*;

public class tb_teacher {
    /*
    *  TB_Teacher table columns names
     */
    public static String TABLE_TEACHER = "TB_Teacher";
    public static String COLUMN_TEACHER_EMAIL = "Email";
    public static String COLUMN_TEACHER_ID = "TB_User_ID";

  /*
  * Getters and Setters
   */



    public static String getTeacherEmail(String id){
        String email = null;
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_TEACHER_EMAIL + " FROM " + TABLE_TEACHER + " WHERE " + COLUMN_TEACHER_ID + " = '" + id + "'");
            while (results.next()) {
                email = results.getString(COLUMN_TEACHER_EMAIL);
            }
            results.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return email;
    }

    public static void setTeacherPassword(String id, String password){
        try{
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = '" + password + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setTeacherName(String id, String name){
        try{
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_NAME + " = '" + name + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setTeacherEmail(String id, String email){
        try {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_TEACHER + " SET " + COLUMN_TEACHER_EMAIL + " = '" + email + "' WHERE " + COLUMN_TEACHER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setTeacherType(String id, String type){
        try{
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_USER + " SET " + COLUMN_USER_TYPE + " = '" + type + "' WHERE " + COLUMN_USER_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /*
    * Methods
    */
    public static void addTeacher(String id, String password, String name, String type, String email) {
    /*
     * This method adds a teacher to the database
     */

    addUser(id, password, name, type);

    String sql = "INSERT INTO " +
            TABLE_TEACHER + " (" + COLUMN_TEACHER_ID + ", " +
            COLUMN_TEACHER_EMAIL + ") VALUES (?, ?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, id);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Query failed: " + e.getMessage());
    }
}

    public static void dropTeacher(String id) {
        /*
         * This method drops a teacher from the database
         */

        dropUser(id);

        String sql = "DELETE FROM " + TABLE_TEACHER + " WHERE " + COLUMN_TEACHER_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllTeachers() {
        /*
         * This method prints all teachers in the database
         */

        String sql = "SELECT * FROM " + TABLE_TEACHER + " INNER JOIN " + TABLE_USER + " ON " + TABLE_TEACHER + "." + COLUMN_TEACHER_ID + " = " + TABLE_USER + "." + COLUMN_USER_ID;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString(COLUMN_USER_ID) + "\t" +
                        rs.getString(COLUMN_USER_PASSWORD) + "\t" +
                        rs.getString(COLUMN_USER_NAME) + "\t" +
                        rs.getString(COLUMN_USER_TYPE) + "\t" +
                        rs.getString(COLUMN_TEACHER_EMAIL));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }


    }

    public static void printTeacherById(String id){
        printUserById(id);
        System.out.println("Email: " + getTeacherEmail(id));
    }




}
