package com.ufv.project.db;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB implements AutoCloseable
{
    private static final String DB_NAME = "get_poc";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final String SET_UP_FOLDER = "src/main/resources/com/ufv/project/db";

    private Connection conn;

    public ConnectDB() throws SQLException
    {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD))
        {
            executeSQLFilesFromFolder(conn, new File(SET_UP_FOLDER));
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
    public void close() throws SQLException
    {
        if (conn != null)
        {
            conn.close();
        }
    }

    public void executeSQLFilesFromFolder(Connection conn, final File folder)
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

            try
            {
                sr.runScript(new BufferedReader(new FileReader(fileEntry.getAbsoluteFile())));
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

}