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
    private static final String DELETE_POC_HAS_KEYWORD = "DELETE FROM " + TABLE_POC_HAS_KEYWORD + " WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ?";

    private final Connection conn;

    public POC_has_KeywordDB(Connection conn)
    {
        this.conn = conn;
    }

    public List<String> queryKeywordsByPOCID(int POCID) throws SQLException
    {
        try (PreparedStatement queryKeywordsByPOCId = conn.prepareStatement(QUERY_KEYWORDS_BY_POC_ID))
        {
            queryKeywordsByPOCId.setInt(1, POCID);

            try (ResultSet results = queryKeywordsByPOCId.executeQuery())
            {
                List<String> keywords = new ArrayList<>();
                KeywordDB keywordDB = new KeywordDB(conn);

                while (results.next())
                {
                    keywords.add(keywordDB.queryKeywordByID(results.getString(COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX)));
                }

                return keywords;
            }
        }
    }

    public List<Integer> queryAllPOC_has_Keyword() throws SQLException
    {
        try (PreparedStatement queryAllPOC_has_Keyword = conn.prepareStatement(QUERY_ALL_POC_HAS_KEYWORD);
             ResultSet results = queryAllPOC_has_Keyword.executeQuery())
        {
            List<Integer> poc_has_keyword = new ArrayList<>();

            while (results.next())
            {
                poc_has_keyword.add(results.getInt(COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX));
            }

            return poc_has_keyword;
        }
    }

    public void insertPOC_has_Keyword(int POCID, String keyword) throws SQLException
    {
        try (PreparedStatement insertPOC_has_Keyword = conn.prepareStatement(INSERT_POC_HAS_KEYWORD, Statement.RETURN_GENERATED_KEYS))
        {
            insertPOC_has_Keyword.setInt(1, POCID);
            insertPOC_has_Keyword.setString(2, keyword);

            if (insertPOC_has_Keyword.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert POC_has_Keyword with POC ID: '" + POCID + "' and keyword: '" + keyword + "'.");
            }
        }
    }

    public void deletePOC_has_Keyword(int POCID) throws SQLException
    {
        try (PreparedStatement deletePOC_has_Keyword = conn.prepareStatement(DELETE_POC_HAS_KEYWORD))
        {
            deletePOC_has_Keyword.setInt(1, POCID);

            if (deletePOC_has_Keyword.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete POC_has_Keyword with POC ID: '" + POCID + "'.");
            }
        }
    }

    public void updatePOC_has_Keyword(int oldPOCID, String oldKeyword, int newPOCID, String newKeyword) throws SQLException
    {
        try (PreparedStatement updatePOC_has_Keyword = conn.prepareStatement(UPDATE_POC_HAS_KEYWORD))
        {
            updatePOC_has_Keyword.setInt(1, newPOCID);
            updatePOC_has_Keyword.setString(2, newKeyword);
            updatePOC_has_Keyword.setInt(3, oldPOCID);
            updatePOC_has_Keyword.setString(4, oldKeyword);

            if (updatePOC_has_Keyword.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update POC_has_Keyword with POC ID: '" + oldPOCID + "' and keyword: '" + oldKeyword + "'.");
            }
        }
    }

}
