//package com.ufv.project.db;
//
//import java.sql.*;
//
//public class KeywordsDB
//{
//    private static final String TABLE_KEYWORDS = "TB_Keywords";
//    private static final String COLUMN_KEYWORDS_ID = "ID";
//    private static final String COLUMN_KEYWORDS_WORD = "Word";
//
//    private static final int COLUMN_KEYWORDS_ID_INDEX = 1;
//    private static final int COLUMN_KEYWORDS_WORD_INDEX = 2;
//
//    private static final String INSERT_KEYWORD = "INSERT INTO " + TABLE_KEYWORDS + " (" + COLUMN_KEYWORDS_ID + ") VALUES (?)";
//
//    private static final String DELETE_KEYWORD = "DELETE FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";
//
//    private static final String GET_ALL_KEYWORDS = "SELECT * FROM " + TABLE_KEYWORDS;
//
//    private PreparedStatement insertKeyword;
//    private PreparedStatement updateKeyword;
//    private PreparedStatement deleteKeyword;
//    private PreparedStatement getKeyword;
//
//    private Connection conn;
//
//    public KeywordsDB(Connection conn)
//    {
//        this.conn = conn;
//
//        try
//        {
//            insertKeyword = conn.prepareStatement(INSERT_KEYWORD, Statement.RETURN_GENERATED_KEYS);
//            deleteKeyword = conn.prepareStatement(DELETE_KEYWORD);
//            getKeyword = conn.prepareStatement(DELETE_KEYWORD);
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void insertKeyword(Keyword) throws SQLException
//    {
//        insertKeyword.setString(COLUMN_KEYWORDS_ID_INDEX, field.getName());
//
//        ResultSet resultSet = insertField.executeQuery();
//
//        if (resultSet.next())
//        {
//            return resultSet.getInt(COLUMN_FIELD_ID_INDEX);
//        }
//        else
//        {
//            System.out.println("Error when inserting area.");
//        }
//
//        return -1;
//    }
//
//    public static void deleteKeyword(String id)
//    {
//        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(DELETE_KEYWORDS))
//        {
//            pstmt.setString(1, id);
//            pstmt.executeUpdate();
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Query failed: " + e.getMessage());
//        }
//    }
//
//    public static void printAllKeywords()
//    {
//        /*
//         * This method prints all keywords in the database
//         */
//
//        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(GET_ALL_KEYWORDS))
//        {
//            while (rs.next())
//            {
//                System.out.println("ID: " + rs.getString(COLUMN_KEYWORDS_ID));
//            }
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Query failed: " + e.getMessage());
//        }
//    }
//
//    public static void printKeywordById(String id)
//    {
//        /*
//         * This method prints a keyword in the database
//         */
//        String sql = "SELECT * FROM " + TABLE_KEYWORDS + " WHERE " + COLUMN_KEYWORDS_ID + " = ?";
//        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql))
//        {
//            pstmt.setString(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next())
//            {
//                System.out.println("ID: " + rs.getString(COLUMN_KEYWORDS_ID));
//            }
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Query failed: " + e.getMessage());
//        }
//    }
//
//    //verify if keyword exists
//}
