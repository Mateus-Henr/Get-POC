package com.ufv.project.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordDB {

    /* Table Keyword constants. */
    private static final String TABLE_KEYWORD = "TB_keyword";

    private static final String COLUMN_KEYWORDS_WORD = "word";

    /* Table Keyword constants indexes. */
    private static final int COLUMN_KEYWORDS_WORD_INDEX = 1;

    /* Table Keyword queries. */
    private static final String QUERY_KEYWORD = "SELECT * FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";
    private static final String QUERY_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORD;
    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORD + " (" + COLUMN_KEYWORDS_WORD + ") VALUES (?)";
    private static final String UPDATE_KEYWORD = "UPDATE " + TABLE_KEYWORD + " SET " + COLUMN_KEYWORDS_WORD + " = ?" + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";
    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORD + " WHERE " + COLUMN_KEYWORDS_WORD + " = ?";

    /* Connection to the database. */
    private final Connection conn;

    public KeywordDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * Query a keyword by its word.
     *
     * @param word word of the keyword to query.
     * @return keyword with the given word.
     * @throws SQLException if the query fails.
     */
    public String queryKeywordByID(String word) throws SQLException {
        try (PreparedStatement queryKeyword = conn.prepareStatement(QUERY_KEYWORD)) {
            queryKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, word);

            try (ResultSet resultSet = queryKeyword.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX);
                }

                return null;
            }
        }
    }

    /**
     * Query all keywords.
     *
     * @return list of all keywords.
     * @throws SQLException if the query fails.
     */

    public List<String> queryKeywords() throws SQLException {
        try (PreparedStatement queryKeywords = conn.prepareStatement(QUERY_KEYWORDS);
             ResultSet resultSet = queryKeywords.executeQuery()) {
            List<String> keywords = new ArrayList<>();

            while (resultSet.next()) {
                keywords.add(resultSet.getString(COLUMN_KEYWORDS_WORD_INDEX));
            }

            return keywords;
        }
    }

    /**
     * Insert a new keyword.
     *
     * @param keyword keyword to insert.
     * @return keyword inserted.
     * @throws SQLException if the insert fails.
     */

    public String insertKeyword(String keyword) throws SQLException {
        try (PreparedStatement insertKeyword = conn.prepareStatement(INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS)) {
            insertKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, keyword);

            if (insertKeyword.executeUpdate() != 1) {
                throw new SQLException("ERROR: Couldn't insert keyword with value: '" + keyword + "'.");
            }

            return keyword;
        }
    }

    /**
     * Delete a keyword.
     *
     * @param id keyword to delete.
     * @return keyword deleted.
     * @throws SQLException if delete fails.
     */

    public String deleteKeyword(String id) throws SQLException {
        try (PreparedStatement deleteKeyword = conn.prepareStatement(DELETE_KEYWORD)) {
            deleteKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, id);

            if (deleteKeyword.executeUpdate() != 1) {
                throw new SQLException("ERROR: Couldn't delete keyword with ID: '" + id + "'.");
            }

            return id;
        }
    }

    /**
     * Update a keyword.
     *
     * @param oldKeyword old word of the keyword to update.
     * @param newKeyword new word of the keyword to update.
     * @return updated keyword.
     * @throws SQLException if update fails.
     */

    public String updateKeyword(String oldKeyword, String newKeyword) throws SQLException {
        try (PreparedStatement updateKeyword = conn.prepareStatement(UPDATE_KEYWORD)) {
            updateKeyword.setString(COLUMN_KEYWORDS_WORD_INDEX, newKeyword);
            updateKeyword.setString(2, oldKeyword);

            if (updateKeyword.executeUpdate() != 1) {
                throw new SQLException("ERROR: Could not update keyword with value: '" + oldKeyword + "'.");
            }
            return newKeyword;
        }
    }

}


