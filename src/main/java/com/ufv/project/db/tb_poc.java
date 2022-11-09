package com.ufv.project.db;

import java.sql.*;
import java.util.Calendar;

import static com.ufv.project.db.ConnectDB.connect;

public class tb_poc
{

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

    public static String getPocTitle(int id)
    {
        String title = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_TITLE + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                title = results.getString(COLUMN_POC_TITLE);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return title;
    }

    public static String getPocDefenseDate(int id)
    {
        String defenseDate = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_DEFENSE_DATE + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                defenseDate = results.getString(COLUMN_POC_DEFENSE_DATE);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return defenseDate;


    }

    public static String getPocSummary(int id)
    {
        String summary = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_SUMMARY + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                summary = results.getString(COLUMN_POC_SUMMARY);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return summary;
    }

    public static int getPocAreaID(int id)
    {
        int areaID = 0;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_AREA_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                areaID = results.getInt(COLUMN_POC_AREA_ID);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return areaID;
    }

    public static int getPocPDFID(int id)
    {
        int pdfID = 0;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_PDF_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                pdfID = results.getInt(COLUMN_POC_PDF_ID);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return pdfID;
    }

    public static String getPocTeacherRegistrant(int id)
    {
        String teacherRegistrant = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_TEACHER_REGISTRANT_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                teacherRegistrant = results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return teacherRegistrant;
    }

    public static String getPocTeacherAdvisor(int id)
    {
        String teacherAdvisor = null;
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT " + COLUMN_POC_TEACHER_ADVISOR_ID + " FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            while (results.next())
            {
                teacherAdvisor = results.getString(COLUMN_POC_TEACHER_ADVISOR_ID);
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return teacherAdvisor;
    }

    public static void setPocTitle(int id, String title)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TITLE + " = '" + title + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setPocDefenseDate(int id, Date date)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_DEFENSE_DATE + " = '" + date + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setPocSummary(int id, String summary)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_SUMMARY + " = '" + summary + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setPocAreaID(int id, int areaID)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_AREA_ID + " = '" + areaID + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void setPocPDFID(int id, int pdfID)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_PDF_ID + " = '" + pdfID + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


    public static void setPocTeacherRegistrant(int id, String teacherRegistrant)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TEACHER_REGISTRANT_ID + " = '" + teacherRegistrant + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


    public static void setPocTeacherAdvisor(int id, String teacherAdvisor)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TEACHER_ADVISOR_ID + " = '" + teacherAdvisor + "' WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


    public static void addPoc(int id, String title, Date defense_date, String summary, int area_id, int pdf_id, String teacher_registrant_id, String teacher_advisor_id)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_AREA_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES ('" + id + "', '" + title + "', '" + defense_date + "', '" + summary + "', '" + area_id + "', '" + pdf_id + "', '" + teacher_registrant_id + "', '" + teacher_advisor_id + "')");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }

    public static void deletePoc(int id)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void printAllPocs()
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC);
            while (results.next())
            {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void printPoc(int id)
    {
        System.out.println("ID: " + id +
                "\nTitle: " + getPocTitle(id) +
                "\nDefense Date: " + getPocDefenseDate(id) +
                "\nSummary: " + getPocSummary(id) +
                "\nArea ID: " + getPocAreaID(id) +
                "\nPDF ID: " + getPocPDFID(id) +
                "\nTeacher Registrant ID: " + getPocTeacherRegistrant(id) +
                "\nTeacher Advisor ID: " + getPocTeacherAdvisor(id));
    }

    public static void existPocID(int id)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = '" + id + "'");
            if (results.next())
            {
                System.out.println("ID: " + results.getInt(COLUMN_POC_ID) + " already exists");
            }
            else
            {
                System.out.println("ID: " + id + " does not exist");
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void searchPocByTextInTitle(String text)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_TITLE + " LIKE '%" + text + "%'");
            while (results.next())
            {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void searchPocByTextInSummary(String text)
    {
        try
        {
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_SUMMARY + " LIKE '%" + text + "%'");
            while (results.next())
            {
                System.out.println(results.getInt(COLUMN_POC_ID) + " " + results.getString(COLUMN_POC_TITLE) + " " + results.getDate(COLUMN_POC_DEFENSE_DATE) + " " + results.getString(COLUMN_POC_SUMMARY) + " " + results.getInt(COLUMN_POC_AREA_ID) + " " + results.getInt(COLUMN_POC_PDF_ID) + " " + results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID) + " " + results.getString(COLUMN_POC_TEACHER_ADVISOR_ID));
            }
            results.close();
            statement.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


}
