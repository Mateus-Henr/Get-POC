package com.ufv.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class User_manages_userDB {

    public static final String TABLE_USER_MANAGES_USER = "tb_user_manages_user";

    public static final String COLUMN_USER_MANAGES_ADMINISTRATOR_ID = "TB_User_Administrator_ID";
    public static final String COLUMN_USER_MANAGES_ADMINISTERED_ID = "TB_User_Administered_ID";


    public static final int COLUMN_USER_MANAGES_ADMINISTRATOR_ID_INDEX = 1;

    public static final int COLUMN_USER_MANAGES_ADMINISTERED_ID_INDEX = 2;

    public static final String INSERT_USER_MANAGES_USER = "INSERT INTO " + TABLE_USER_MANAGES_USER + " (" + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + ", " + COLUMN_USER_MANAGES_ADMINISTERED_ID + ") VALUES (?, ?)";
    public static final String DELETE_USER_MANAGES_USER = "DELETE FROM " + TABLE_USER_MANAGES_USER + " WHERE " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ? AND " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ?";
    public static final String GET_ALL_USER_MANAGES_USER = "SELECT * FROM " + TABLE_USER_MANAGES_USER;
    public static final String UPDATE_USER_MANAGES_USER = "UPDATE " + TABLE_USER_MANAGES_USER + " SET " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ?, " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ? WHERE " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ? AND " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ?";

    private PreparedStatement insertUserManagesUser;

    private PreparedStatement deleteUserManagesUser;

    private PreparedStatement getAllUserManagesUser;

    private PreparedStatement updateUserManagesUser;

    private final Connection conn;

    public User_manages_userDB(Connection conn) {
        this.conn = conn;
        try {
            insertUserManagesUser = conn.prepareStatement(INSERT_USER_MANAGES_USER);
            deleteUserManagesUser = conn.prepareStatement(DELETE_USER_MANAGES_USER);
            getAllUserManagesUser = conn.prepareStatement(GET_ALL_USER_MANAGES_USER);
            updateUserManagesUser = conn.prepareStatement(UPDATE_USER_MANAGES_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //insert +

}

