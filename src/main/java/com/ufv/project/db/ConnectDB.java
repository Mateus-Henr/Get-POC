package com.ufv.project.db;

import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;
import com.ufv.project.model.Student;
import com.ufv.project.model.Subject;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB implements AutoCloseable
{
    private static final String DB_NAME = "get_poc";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String SET_UP_FOLDER = "src/main/resources/com/ufv/project/db";

    private Connection conn;

    public ConnectDB() throws Exception
    {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD))
        {
            readSQLFilesFromFolder(conn, new File(SET_UP_FOLDER));
        }
    }

    public Connection getConnection() throws SQLException
    {
        if (conn != null)
        {
            return conn;
        }

        return DriverManager.getConnection(CONNECTION_STRING + DB_NAME, USER, PASSWORD);
    }

    @Override
    public void close() throws Exception
    {
        if (conn != null)
        {
            conn.close();
        }
    }

    public void readSQLFilesFromFolder(Connection conn, final File folder) throws Exception
    {
        File[] files = folder.listFiles();

        if (files == null)
        {
            return;
        }

        for (final File fileEntry : files)
        {
            ScriptRunner sr = new ScriptRunner(conn);

            sr.setLogWriter(null);
            sr.setErrorLogWriter(null);
            sr.runScript(new BufferedReader(new FileReader(fileEntry.getAbsoluteFile())));
        }
    }

}

class Main
{
    public static void main(String[] args) throws Exception
    {
        ConnectDB connectDB = new ConnectDB();
        connectDB.getConnection();

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
        KeywordDB keywordDB = new KeywordDB(connectDB.getConnection());
        POCDB pocDB = new POCDB(connectDB.getConnection());

        List<Student> students = new ArrayList<>();
        students.add(studentDB.queryStudent("Miguel", "MiguelRib", "123"));


        List<String> keywords = new ArrayList<>();
        keywords.add("ola");
        keywords.add("oi");


        List<Professor> professors = new ArrayList<>();
        professors.add(professorDB.queryProfessor("nacif", "123", "Nacif"));
        professors.add(professorDB.queryProfessor("Fabricio", "1234", "Fabricio"));


        POC.POCBuilder pocBuilder = new POC.POCBuilder();
        pocBuilder.id(2);
        pocBuilder.title("FEWWXWXWWX");
        pocBuilder.authors(students);
        pocBuilder.defenseDate(LocalDate.of(2021, 11, 12));
        pocBuilder.keywords(keywords);
        pocBuilder.summary("Summary wdqqwqdwqqd");
        pocBuilder.field(fieldDB.queryFieldByID(4));
        pocBuilder.pdf(pdfDB.queryPDFByID(3));
        pocBuilder.registrant((Professor) userDB.queryUserByID("fabricio"));
        pocBuilder.advisor((Professor) userDB.queryUserByID("nacif"));
        pocBuilder.coAdvisors(professors);
        POC poc = pocBuilder.build();

        List<POC> pocs = new ArrayList<>();
        pocs = pocDB.queryAllPOCs();
        for (POC poc1 : pocs)
        {
            System.out.println(poc1.getId());
        }


        //printar keywords


    }

}