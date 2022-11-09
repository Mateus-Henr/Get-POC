package com.ufv.project.db;

import java.sql.*;

public class KeywordDB
{

    /*
     * TB_Keyword table names
     */
    public static final String TABLE_KEYWORDS = "TB_Keywords";
    public static final String COLUMN_KEYWORDS_ID = "ID";

    public static final String INSERT_KEYWORDS = "INSERT INTO " + TABLE_KEYWORDS + " (" + COLUMN_KEYWORDS_ID + ") VALUES (?)";

    public static final String DELETE_KEYWORDS = "DELETE FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    public static final String PRINT_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORDS;

    /*
     * Keyword methods
     */

    public static void addKeyword(String id)
    {
        /*
         * This method adds a keyword to the database
         */


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_KEYWORDS))
        {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void dropKeyword(String id)
    {
        /*
         * This method drops a keyword from the database
         */


        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_KEYWORDS))
        {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllKeywords()
    {
        /*
         * This method prints all keywords in the database
         */

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(PRINT_KEYWORDS))
        {
            while (rs.next())
            {
                System.out.println("ID: " + rs.getString(COLUMN_KEYWORDS_ID));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printKeywordById(String id)
    {
        /*
         * This method prints a keyword in the database
         */
        String sql = "SELECT * FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                System.out.println("ID: " + rs.getString(COLUMN_KEYWORDS_ID));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    //verify if keyword exists
}
