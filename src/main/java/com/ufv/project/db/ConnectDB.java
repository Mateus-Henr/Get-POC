package com.ufv.project.db;


import java.sql.*;


public class ConnectDB
{

    public static final String DB_NAME = "get_poc";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String USER = "root";
    public static final String PASSWORD = "123456";


    /*
     * TB_Teacher table columns names
     */

    public static final String TABLE_TEACHER = "TB_Teacher";
    public static final String COLUMN_TEACHER_EMAIL = "Email";
    public static final String COLUMN_TEACHER_ID = "TB_User_ID";


    /*
     * TB_POC table columns names
     */
    public static final String TABLE_POC = "TB_POC";
    public static final String COLUMN_POC_ID = "ID";
    public static final String COLUMN_POC_TITLE = "Title";
    public static final String COLUMN_POC_DEFENSE_DATE = "Defense_Date";
    public static final String COLUMN_POC_SUMMARY = "Summary";
    public static final String COLUMN_POC_AREA_ID = "TB_Area_ID";
    public static final String COLUMN_POC_PDF_ID = "TB_PDF_ID";
    public static final String COLUMN_POC_TEACHER_REGISTRANT_ID = "Teacher_Registrant";
    public static final String COLUMN_POC_TEACHER_ADVISOR_ID = "Teacher_Advisor";

    /*
     * TB_POC_has_Keyword table columns names
     */
    public static final String TABLE_POC_HAS_KEYWORD = "TB_POC_has_Keyword";
    public static final String COLUMN_POC_HAS_KEYWORD_POC_ID = "TB_POC_ID";
    public static final String COLUMN_POC_HAS_KEYWORD_KEYWORD_ID = "TB_Keyword_ID";

    /*
     * TB_Student table columns names
     */
    public static final String TABLE_STUDENT = "TB_Student";
    public static final String COLUMN_STUDENT_EMAIL = "Email";
    public static final String COLUMN_STUDENT_REGISTRATION = "Registration";
    public static final String COLUMN_STUDENT_POC_ID = "POC";
    public static final String COLUMN_STUDENT_USER_ID = "TB_User_ID";

    /*
     * TB_Teacher_co-advises-Poc table columns names
     */

    public static final String TABLE_TEACHER_CO_ADVISES_POC = "TB_Teacher_co-advises_Poc";
    public static final String COLUMN_TABLE_TEACHER_CO_ADVISES_POC_POC_ID = "TB_POC_ID";
    public static final String COLUMN_TABLE_TEACHER_CO_ADVISES_POC_TEACHER_ID = "tb_teacher_TB_User_ID";

    /*
     * TB_Teacher_has_Discipline table columns names
     */
    public static final String TABLE_TEACHER_HAS_DISCIPLINE = "TB_Teacher_has_Discipline";
    public static final String COLUMN_TEACHER_HAS_DISCIPLINE_DISCIPLINE_ID = "TB_Discipline_ID";
    public static final String COLUMN_TEACHER_HAS_DISCIPLINE_TEACHER_ID = "TB_Teacher_User_ID";

    /*
     * TB_User_manages_User table columns names
     */
    public static final String TABLE_USER_MANAGES_USER = "TB_User_manages_User";
    public static final String COLUMN_USER_MANAGES_USER_ADMINISTRATOR_ID = "TB_User_Administrator_ID";
    public static final String COLUMN_USER_MANAGES_USER_ADMINISTERED_ID = "TB_User_Administered_ID";

    private Connection conn;

    public boolean open()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't connect to database " + e.getMessage());
            return false;
        }
    }

    public void close()
    {
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        ConnectDB connectDB = new ConnectDB();

        if (!connectDB.open())
        {
            System.out.println("Connection failed");
            return;
        }


        connectDB.close();
    }

}
