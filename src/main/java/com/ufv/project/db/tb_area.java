package com.ufv.project.db;

import java.sql.*;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_area
{

    /*
     *   TB_Area table columns names
     */
    public static final String TABLE_AREA = "tb_Area";
    public static final String COLUMN_AREA_ID = "ID";
    public static final String COLUMN_AREA_NAME = "Name";

    /*
     * Getters
     */
    public static String getAreaName(int id)
    {
        /*
         * This method returns the name of an area
         */
        String sql = "SELECT " + COLUMN_AREA_NAME + " FROM " + TABLE_AREA + " WHERE " + COLUMN_AREA_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                return rs.getString(COLUMN_AREA_NAME);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }

    public static void setAreaName(int id, String name)
    {
        /*
         * This method updates the name of an area
         */
        String sql = "UPDATE " + TABLE_AREA + " SET " + COLUMN_AREA_NAME + " = ? WHERE " + COLUMN_AREA_ID + " = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    /*
     * Methods
     */

    public static void addArea(int id, String name)
    {
        /*
         * This method adds an area to the database
         */

        String sql = "INSERT INTO " +
                TABLE_AREA + " (" + COLUMN_AREA_ID + ", " +
                COLUMN_AREA_NAME + ") VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void dropArea(int id)
    {
        /*
         * This method drops an area from the database
         */

        String sql = "DELETE FROM " + TABLE_AREA + " WHERE " + COLUMN_AREA_ID + " = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAllAreas()
    {
        /*
         * This method prints all areas in the database
         */
        String sql = "SELECT * FROM " + TABLE_AREA;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                System.out.println(rs.getString(COLUMN_AREA_ID) + "\t" +
                        rs.getString(COLUMN_AREA_NAME));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void printAreaById(int id)
    {
        /*
         * This method prints an area in the database
         */
        System.out.println("ID: " + id
                + "\tName: " + getAreaName(id));
    }


}