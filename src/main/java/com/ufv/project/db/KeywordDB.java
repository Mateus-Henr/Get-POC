package com.ufv.project.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordDB
{
    private static final String TABLE_KEYWORD = "TB_keyword";
    private static final String COLUMN_KEYWORDS_ID = "ID";
    private static final String COLUMN_KEYWORDS_WORD = "word";

    private static final int COLUMN_KEYWORDS_ID_INDEX = 1;
    private static final int COLUMN_KEYWORDS_WORD_INDEX = 2;

    private static final String QUERY_KEYWORD = "SELECT * FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";
    private static final String QUERY_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORD;
    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORD + " (" + COLUMN_KEYWORDS_ID + " , " + COLUMN_KEYWORDS_WORD + ") VALUES (?,?)";
    private static final String UPDATE_KEYWORD = "UPDATE " + TABLE_KEYWORD + " SET " + COLUMN_KEYWORDS_WORD + " = ? WHERE " + COLUMN_KEYWORDS_ID + " = ?";
    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

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

    public String queryKeywordByID(int id) throws SQLException
    {
        queryKeyword.setInt(COLUMN_KEYWORDS_ID_INDEX, id);

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


    public int insertKeyword(int id, String keyword) throws SQLException
    {
        insertKeyword.setInt(COLUMN_KEYWORDS_ID_INDEX, id);
        insertKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, keyword);

        int affectedRows = insertKeyword.executeUpdate();

        if (affectedRows != 1)
        {
            throw new SQLException("Couldn't insert keyword!");
        }

        try (ResultSet generatedKeys = insertKeyword.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                return generatedKeys.getInt(1);
            }
        }

        throw new SQLException("Couldn't get _id for keyword");
    }

    public int deleteKeyword(int id) throws SQLException
    {
        deleteKeyword.setInt(COLUMN_KEYWORDS_ID_INDEX, id);

        return deleteKeyword.executeUpdate();
    }

    public String updateKeyword(int id, String newKeyword) throws SQLException
    {
        String oldKeyword = queryKeywordByID(id);

        updateKeyword.setInt(COLUMN_KEYWORDS_WORD_INDEX, id);
        updateKeyword.setString(COLUMN_KEYWORDS_ID_INDEX, newKeyword);

        int affectedRows = updateKeyword.executeUpdate();

        if (affectedRows == 1)
        {
            return oldKeyword;
        }

        return null;
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


