package com.ufv.project.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordDB
{
    private static final String TABLE_KEYWORD = "TB_keyword";

    private static final String COLUMN_KEYWORDS_WORD = "word";

    private static final int COLUMN_KEYWORDS_WORD_INDEX = 1;

    private static final String QUERY_KEYWORD = "SELECT * FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";
    private static final String QUERY_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORD;
    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORD + " (" + COLUMN_KEYWORDS_WORD + ") VALUES (?)";
    private static final String UPDATE_KEYWORD = "UPDATE " + TABLE_KEYWORD + " SET " + COLUMN_KEYWORDS_WORD + " = ?" + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";
    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";

    private Connection conn;

    private final PreparedStatement queryKeyword;
    private final PreparedStatement queryKeywords;
    private final PreparedStatement insertKeyword;
    private final PreparedStatement updateKeyword;
    private final PreparedStatement deleteKeyword;

    public KeywordDB(Connection conn) throws SQLException
    {
        this.conn = conn;

        queryKeyword = conn.prepareStatement(QUERY_KEYWORD);
        queryKeywords = conn.prepareStatement(QUERY_KEYWORDS);
        insertKeyword = conn.prepareStatement(INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS);
        deleteKeyword = conn.prepareStatement(DELETE_KEYWORD);
        updateKeyword = conn.prepareStatement(UPDATE_KEYWORD);
    }

    public String queryKeywordByID(String word) throws SQLException
    {
        queryKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, word);

        try (ResultSet resultSet = queryKeyword.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX);
            }
        }

        return null;
    }

    public List<String> queryKeywords() throws SQLException
    {
        try (ResultSet resultSet = queryKeywords.executeQuery())
        {
            List<String> keywords = new ArrayList<>();

            while (resultSet.next())
            {
                keywords.add(resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX));
            }

            return keywords;
        }
    }


    public String insertKeyword(String keyword) throws SQLException
    {
        insertKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, keyword);

        int affectedRows = insertKeyword.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Could not insert keyword");
        }

        return keyword;
    }

    public String deleteKeyword(String id) throws SQLException
    {
        deleteKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, id);


        int affectedRows = deleteKeyword.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Could not delete keyword");
        }

        return id;
    }

    public String updateKeyword(String oldkeyword, String newKeyword) throws SQLException
    {
        updateKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, newKeyword);
        updateKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX + 1, oldkeyword);

        int affectedRows = updateKeyword.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Could not update keyword");
        }

        return newKeyword;
    }

    public void close() throws SQLException
    {
        if (queryKeyword != null)
        {
            queryKeyword.close();
        }
        if (queryKeywords != null)
        {
            queryKeywords.close();
        }
        if (insertKeyword != null)
        {
            insertKeyword.close();
        }
        if (deleteKeyword != null)
        {
            deleteKeyword.close();
        }
        if (updateKeyword != null)
        {
            updateKeyword.close();
        }
    }

}


