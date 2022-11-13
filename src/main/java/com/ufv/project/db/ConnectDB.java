package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB
{
    public static final String DB_NAME = "get_poc";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    private Connection conn;

    public boolean open()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
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

    public Connection getConnection()
    {
        return conn;
    }

    public static void main(String[] args) throws SQLException
    {

        ConnectDB connectDB = new ConnectDB();
        connectDB.open();

        List<Subject> subjects = new ArrayList<>();


        SubjectDB subjectDB = new SubjectDB(connectDB.getConnection());
        Professor_has_subjectDB professor_has_subjectDB = new Professor_has_subjectDB(connectDB.getConnection());
        ProfessorDB professorDB = new ProfessorDB(connectDB.getConnection());
        POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(connectDB.getConnection());
        UserDB userDB = new UserDB(connectDB.getConnection());
        StudentDB studentDB = new StudentDB(connectDB.getConnection());
        FieldDB fieldDB = new FieldDB(connectDB.getConnection());
        PDFDB pdfDB = new PDFDB(connectDB.getConnection());
        Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(connectDB.getConnection());


        POCDB pocDB = new POCDB(connectDB.getConnection());

        List <Student> students = studentDB.queryStudentsbyPocID(1);
        for (Student student : students)
        {
            System.out.println(student.getName());
        }

        List <Professor> professors = professor_co_advises_pocDB.queryProfessorsByPocId(1);
        for (Professor professor : professors)
        {
            System.out.println(professors.get(0).getName());
        }










        POC.POCBuilder pocBuilder = new POC.POCBuilder();
        pocBuilder.id(1);
        pocBuilder.title("POC 1");
        pocBuilder.authors(studentDB.queryStudentsbyPocID(1));
        pocBuilder.defenseDate(LocalDate.of(2020, 12, 12));
        pocBuilder.keywords(poc_has_keywordDB.queryKeywordsByPOCID(1));
        pocBuilder.summary("Summary 1");
        pocBuilder.field(fieldDB.queryFieldByID(2));
        pocBuilder.pdf(pdfDB.queryPDFByID(1));
        pocBuilder.registrant( (Professor) userDB.queryUserByID("nacif"));
        pocBuilder.advisor( (Professor) userDB.queryUserByID("jose"));
        pocBuilder.coAdvisors(professors);

        System.out.println(pocBuilder.build().getCoAdvisors().get(0).getName());
        










    }

}