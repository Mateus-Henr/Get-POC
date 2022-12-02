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
    // Database config.
    private static final String DB_NAME = "get_poc";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // File to set up database.
    private static final String DB_SET_UP_FILE = "src/main/resources/com/ufv/project/db/get_poc_tables.sql";

    private Connection conn;

    /**
     * Initializes database.
     *
     * @throws SQLException          if it doesn't manage to connect to the database.
     * @throws FileNotFoundException if it doesn't find the file to set up the database.
     */
    public static void initializeDB() throws SQLException, FileNotFoundException
    {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD))
        {
            executeSQLScript(conn, new File(DB_SET_UP_FILE));
        }
    }

    /**
     * Gets a Connection object.
     *
     * @return Connection object.
     * @throws SQLException if it doesn't manage to get the Connection object.
     */
    public Connection getConnection() throws SQLException
    {
        if (conn != null)
        {
            return conn;
        }

        return DriverManager.getConnection(CONNECTION_STRING + DB_NAME, USER, PASSWORD);
    }

    /**
     * Initializes database.
     *
     * @throws SQLException if it doesn't manage to close the connection.
     */
    @Override
    public void close() throws SQLException
    {
        if (conn != null)
        {
            conn.close();
        }
    }

    /**
     * Executes a SQL script using ScriptRunner class.
     *
     * @param conn      Connection object.
     * @param SQLScript script file to be run.
     */
    public static void executeSQLScript(Connection conn, final File SQLScript) throws FileNotFoundException
    {
        if (SQLScript == null)
        {
            return;
        }

        ScriptRunner sr = new ScriptRunner(conn);

        // Disables error messages.
        sr.setLogWriter(null);
        sr.setErrorLogWriter(null);

        sr.runScript(new BufferedReader(new FileReader(SQLScript.getAbsoluteFile())));
    }

}