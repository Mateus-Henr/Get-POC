package com.ufv.project.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class POC_has_KeywordDB {

    public final static String TABLE_POC_HAS_KEYWORD = "tb_poc_has_keyword";

    public final static String COLUMN_POC_HAS_KEYWORD_POC_ID = "TB_POC_ID";

    public final static String COLUMN_POC_HAS_KEYWORD_KEYWORD_ID = "TB_keyword_ID";

    public static final int COLUMN_POC_HAS_KEYWORD_POC_ID_INDEX = 1;

    public static final int COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX = 2;

    public static final String INSERT_POC_HAS_KEYWORD = "INSERT INTO " + TABLE_POC_HAS_KEYWORD + " (" + COLUMN_POC_HAS_KEYWORD_POC_ID + ", " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + ") VALUES (?, ?)";

    public static final String DELETE_POC_HAS_KEYWORD = "DELETE FROM " + TABLE_POC_HAS_KEYWORD + " WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ? AND " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ?";

    public static final String GET_ALL_POC_HAS_KEYWORD = "SELECT * FROM " + TABLE_POC_HAS_KEYWORD;

    public static final String UPDATE_POC_HAS_KEYWORD = "UPDATE " + TABLE_POC_HAS_KEYWORD + " SET " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ?, " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ? WHERE " + COLUMN_POC_HAS_KEYWORD_POC_ID + " = ? AND " + COLUMN_POC_HAS_KEYWORD_KEYWORD_ID + " = ?";

    private PreparedStatement insertPOC_has_Keyword;
    private PreparedStatement updatePOC_has_Keyword;
    private PreparedStatement deletePOC_has_Keyword;
    private PreparedStatement getPOC_has_Keyword;

    private final Connection conn;

    public POC_has_KeywordDB(Connection conn) {
        this.conn = conn;

        try {
            insertPOC_has_Keyword = conn.prepareStatement(INSERT_POC_HAS_KEYWORD, Statement.RETURN_GENERATED_KEYS);
            updatePOC_has_Keyword = conn.prepareStatement(UPDATE_POC_HAS_KEYWORD);
            deletePOC_has_Keyword = conn.prepareStatement(DELETE_POC_HAS_KEYWORD);
            getPOC_has_Keyword = conn.prepareStatement(GET_ALL_POC_HAS_KEYWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //como cria isso

  /*public void insertPOC_has_Keyword(int poc_id, int keyword_id) throws SQLException {
        insertPOC_has_Keyword.setInt(COLUMN_POC_HAS_KEYWORD_POC_ID_INDEX, poc_id);
        insertPOC_has_Keyword.setInt(COLUMN_POC_HAS_KEYWORD_KEYWORD_ID_INDEX, keyword_id);


            }*/






}
