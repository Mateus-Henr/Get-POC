package com.ufv.project.db;

import com.ufv.project.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordsDB
{
    private static final String TABLE_KEYWORDS = "TB_Keywords";
    private static final String COLUMN_KEYWORDS_ID = "ID";
    private static final String COLUMN_KEYWORDS_WORD = "Word";

    private static final int COLUMN_KEYWORDS_ID_INDEX = 1;
    private static final int COLUMN_KEYWORDS_WORD_INDEX = 2;

    private static final String GET_KEYWORD = "SELECT * FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    private static final String GET_ALL_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORDS;

    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORDS + " (" + COLUMN_KEYWORDS_ID + ") VALUES (?)";

    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    private PreparedStatement insertKeyword;
    private PreparedStatement updateKeyword;
    private PreparedStatement deleteKeyword;
    private PreparedStatement getKeyword;

    private Connection conn;

    public KeywordsDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getKeyword = conn.prepareStatement(GET_KEYWORD);
            insertKeyword = conn.prepareStatement(INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS);
            deleteKeyword = conn.prepareStatement(DELETE_KEYWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int insertKeyword(String keyword) throws SQLException
    {
        insertKeyword.setString(COLUMN_KEYWORDS_ID_INDEX, keyword);

        try (ResultSet resultSet = insertKeyword.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getInt(COLUMN_KEYWORDS_ID_INDEX);
            }
            else
            {
                System.out.println("Error when inserting area.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return -1;
    }

    public String deleteKeyword(String word) throws SQLException
    {
        deleteKeyword.setString(1, word);

        try (ResultSet resultSet = deleteKeyword.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX);
            }
            else
            {
                System.out.println("Error when deleting discipline.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getAllKeywords()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_KEYWORD))
        {
            List<String> keywords = new ArrayList<>();

            while (resultSet.next())
            {
                keywords.add(resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX));
            }

            return keywords;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

}
