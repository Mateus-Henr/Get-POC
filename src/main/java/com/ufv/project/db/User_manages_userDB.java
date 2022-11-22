package com.ufv.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User_manages_userDB
{
    private static final String TABLE_USER_MANAGES_USER = "tb_user_manages_user";

    private static final String COLUMN_USER_MANAGES_ADMINISTRATOR_ID = "TB_User_Administrator_ID";
    private static final String COLUMN_USER_MANAGES_ADMINISTERED_ID = "TB_User_Administered_ID";

    private static final int COLUMN_USER_MANAGES_ADMINISTRATOR_ID_INDEX = 1;
    private static final int COLUMN_USER_MANAGES_ADMINISTERED_ID_INDEX = 2;

    private static final String GET_ALL_USER_MANAGES_USER = "SELECT * FROM " + TABLE_USER_MANAGES_USER;
    private static final String INSERT_USER_MANAGES_USER = "INSERT INTO " + TABLE_USER_MANAGES_USER + " (" + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + ", " + COLUMN_USER_MANAGES_ADMINISTERED_ID + ") VALUES (?, ?)";
    private static final String UPDATE_USER_MANAGES_USER = "UPDATE " + TABLE_USER_MANAGES_USER + " SET " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ?, " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ? WHERE " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ? AND " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ?";
    private static final String DELETE_USER_MANAGES_USER = "DELETE FROM " + TABLE_USER_MANAGES_USER + " WHERE " + COLUMN_USER_MANAGES_ADMINISTRATOR_ID + " = ? AND " + COLUMN_USER_MANAGES_ADMINISTERED_ID + " = ?";

    private PreparedStatement getAllUserManagesUser;
    private PreparedStatement insertUserManagesUser;
    private PreparedStatement updateUserManagesUser;
    private PreparedStatement deleteUserManagesUser;

    public User_manages_userDB(Connection conn) throws SQLException
    {
        insertUserManagesUser = conn.prepareStatement(INSERT_USER_MANAGES_USER);
        deleteUserManagesUser = conn.prepareStatement(DELETE_USER_MANAGES_USER);
        getAllUserManagesUser = conn.prepareStatement(GET_ALL_USER_MANAGES_USER);
        updateUserManagesUser = conn.prepareStatement(UPDATE_USER_MANAGES_USER);
    }


    //insert +

}

