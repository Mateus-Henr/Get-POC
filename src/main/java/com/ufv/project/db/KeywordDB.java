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
    private static final String QUERY_KEYWORD = "SELECT " + COLUMN_KEYWORDS_ID + " FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    private static final String QUERY_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORD;
    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORD + " (" + COLUMN_KEYWORDS_ID + " , " + COLUMN_KEYWORDS_WORD + ") VALUES (?,?)";
    private static final String UPDATE_KEYWORD = "UPDATE " + TABLE_KEYWORD + " SET " + COLUMN_KEYWORDS_WORD + " = ? WHERE " + COLUMN_KEYWORDS_ID + " = ?";

    private PreparedStatement insertKeyword;
    private PreparedStatement updateKeyword;
    private PreparedStatement deleteKeyword;
    private PreparedStatement queryKeyword;

    private Connection conn;

    public KeywordDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            insertKeyword = conn.prepareStatement(INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS);
            deleteKeyword = conn.prepareStatement(DELETE_KEYWORD);
            updateKeyword = conn.prepareStatement(UPDATE_KEYWORD);
            queryKeyword = conn.prepareStatement(QUERY_KEYWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int queryKeywordByID(int id) throws SQLException
    {
        queryKeyword.setInt(COLUMN_KEYWORDS_ID_INDEX, id);;

        try (ResultSet resultSet = queryKeyword.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getInt(COLUMN_KEYWORDS_ID_INDEX);
            }
            else
            {
                return -1;
            }
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
            else
            {
                throw new SQLException("Couldn't get _id for keyword");
            }
        }

    }

    public int deleteKeyword(int id) throws SQLException
    {
        deleteKeyword.setInt(COLUMN_KEYWORDS_ID_INDEX, id);
        return deleteKeyword.executeUpdate();
    }

    public int updateKeywordByID(int id, String keyword) throws SQLException
    {

        int idKeyword = queryKeywordByID(id);
        if (idKeyword == -1)
        {
            System.out.println("Keyword not found");
            return -1;
        }


        updateKeyword.setString(COLUMN_KEYWORDS_ID_INDEX, keyword);
        updateKeyword.setInt(COLUMN_KEYWORDS_WORD_INDEX, id);
        return updateKeyword.executeUpdate();

    }

    public List<String> getKeywords()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_KEYWORDS))
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
