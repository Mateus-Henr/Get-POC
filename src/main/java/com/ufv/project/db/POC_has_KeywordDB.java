package com.ufv.project.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class POC_has_KeywordDB
{
    private static final String TABLE_POC_HAS_KEYWORD = "tb_poc_has_keyword";

    private static final String COLUMN_POC_HAS_KEYWORD_POC_ID = "TB_POC_ID";
    private static final String COLUMN_POC_HAS_KEYWORD_KEYWORD_ID = "TB_keyword_ID";

    private static final int COLUMN_POC_HAS_KEYWORD_POC_ID_INDEX = 1;
    private static final int COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX = 2;

    private static final String QUERY_KEYWORDS_BY_POC_ID = "SELECT * FROM " + TABLE_POC_HAS_KEYWORD + " WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ?";
    private static final String QUERY_ALL_POC_HAS_KEYWORD = "SELECT * FROM " + TABLE_POC_HAS_KEYWORD;
    private static final String INSERT_POC_HAS_KEYWORD = "INSERT INTO " + TABLE_POC_HAS_KEYWORD + " (" + COLUMN_POC_HAS_KEYWORD_POC_ID + ", " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + ") VALUES (?, ?)";
    private static final String UPDATE_POC_HAS_KEYWORD = "UPDATE " + TABLE_POC_HAS_KEYWORD + " SET " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ?, " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ? WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ? AND " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ?";
    private static final String DELETE_POC_HAS_KEYWORD = "DELETE FROM " + TABLE_POC_HAS_KEYWORD + " WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ? AND " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ?";

    private PreparedStatement queryKeywordsByPOCId;
    private PreparedStatement queryAllPOC_has_Keyword;
    private PreparedStatement insertPOC_has_Keyword;
    private PreparedStatement updatePOC_has_Keyword;
    private PreparedStatement deletePOC_has_Keyword;

    private final Connection conn;

    public POC_has_KeywordDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            queryKeywordsByPOCId = conn.prepareStatement(QUERY_KEYWORDS_BY_POC_ID);
            queryAllPOC_has_Keyword = conn.prepareStatement(QUERY_ALL_POC_HAS_KEYWORD);
            insertPOC_has_Keyword = conn.prepareStatement(INSERT_POC_HAS_KEYWORD, Statement.RETURN_GENERATED_KEYS);
            deletePOC_has_Keyword = conn.prepareStatement(DELETE_POC_HAS_KEYWORD);
            updatePOC_has_Keyword = conn.prepareStatement(UPDATE_POC_HAS_KEYWORD);
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't prepare statement: " + e.getMessage());
        }
    }

    public List<String> queryKeywordsByPOCID(int poc_id)
    {
        try
        {
            KeywordDB keywordDB = new KeywordDB(conn);
            queryKeywordsByPOCId.setInt(1, poc_id);

            ResultSet results = queryKeywordsByPOCId.executeQuery();
            List<String> keywords = new ArrayList<>();

            while (results.next())
            {
                keywords.add(keywordDB.queryKeywordByID(results.getInt(COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX)));
            }

            return keywords;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

    public List<Integer> queryAllPOC_has_Keyword()
    {
        try (ResultSet results = queryAllPOC_has_Keyword.executeQuery())
        {
            List<Integer> poc_has_keyword = new ArrayList<>();

            while (results.next())
            {
                poc_has_keyword.add(results.getInt(COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX));
            }

            return poc_has_keyword;
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't query all POC_has_Keyword: " + e.getMessage());
        }

        return null;
    }

    public void insertPOC_has_Keyword(int poc_id, int keyword_id)
    {
        try
        {
            insertPOC_has_Keyword.setInt(1, poc_id);
            insertPOC_has_Keyword.setInt(2, keyword_id);

            System.out.println(insertPOC_has_Keyword.toString());

            int affectedRows = insertPOC_has_Keyword.executeUpdate();

            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't insert POC_has_Keyword!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't insert POC_has_Keyword: " + e.getMessage());
        }
    }

    public void deletePOC_has_Keyword(int poc_id, int keyword_id)
    {
        try
        {
            deletePOC_has_Keyword.setInt(1, poc_id);
            deletePOC_has_Keyword.setInt(2, keyword_id);

            int affectedRows = deletePOC_has_Keyword.executeUpdate();

            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't delete POC_has_Keyword!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't delete POC_has_Keyword: " + e.getMessage());
        }
    }

    public void updatePOC_has_Keyword(int poc_id, int keyword_id, int new_poc_id, int new_keyword_id)
    {
        try
        {
            updatePOC_has_Keyword.setInt(1, new_poc_id);
            updatePOC_has_Keyword.setInt(2, new_keyword_id);
            updatePOC_has_Keyword.setInt(3, poc_id);
            updatePOC_has_Keyword.setInt(4, keyword_id);

            int affectedRows = updatePOC_has_Keyword.executeUpdate();

            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't update POC_has_Keyword!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't update POC_has_Keyword: " + e.getMessage());
        }

    }

    public void close()
    {
        try
        {
            if (queryKeywordsByPOCId != null)
            {
                queryKeywordsByPOCId.close();
            }
            if (queryAllPOC_has_Keyword != null)
            {
                queryAllPOC_has_Keyword.close();
            }
            if (insertPOC_has_Keyword != null)
            {
                insertPOC_has_Keyword.close();
            }
            if (deletePOC_has_Keyword != null)
            {
                deletePOC_has_Keyword.close();
            }
            if (updatePOC_has_Keyword != null)
            {
                updatePOC_has_Keyword.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

}
