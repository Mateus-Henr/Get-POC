package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    /* Table User constants. */
    private static final String TABLE_USER = "TB_User";
    private static final String COLUMN_USER_ID = "ID";
    private static final String COLUMN_USER_PASSWORD = "Password";
    private static final String COLUMN_USER_NAME = "Name";
    private static final String COLUMN_USER_TYPE = "Type";

    /* Table User constants indexes. */

    private static final int COLUMN_USER_ID_INDEX = 1;
    private static final int COLUMN_USER_PASSWORD_INDEX = 2;
    private static final int COLUMN_USER_NAME_INDEX = 3;
    private static final int COLUMN_USER_TYPE_INDEX = 4;

    /* Table User queries. */

    private static final String QUERY_USER = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    private static final String QUERY_USERS = "SELECT * FROM " + TABLE_USER;
    private static final String INSERT_USER = "INSERT INTO " + TABLE_USER + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_TYPE + ") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_TYPE + " = ? WHERE " + COLUMN_USER_ID + " = ?";
    private static final String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    private static final String SEARCH_USERS_BY_CONTAINS_ID = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " LIKE ?";

    /* Connection to the database. */

    private final Connection conn;

    public UserDB(Connection conn) {
        this.conn = conn;
    }

    /* Query user by ID.
    *
    * @param    id     username of the user to query.
    * @return   user with the given ID.
     */

    public User queryUserByID(String id) throws SQLException {
        try (PreparedStatement queryUser = conn.prepareStatement(QUERY_USER)) {
            queryUser.setString(COLUMN_USER_ID_INDEX, id);

            try (ResultSet resultSet = queryUser.executeQuery()) {
                if (resultSet.next()) {
                    UserTypesEnum userType = UserTypesEnum.valueOf(resultSet.getString(COLUMN_USER_TYPE_INDEX));

                    if (userType == UserTypesEnum.PROFESSOR) {
                        return new ProfessorDB(conn).queryProfessor(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX));
                    } else if (userType == UserTypesEnum.ADMIN) {
                        return new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                    } else if (userType == UserTypesEnum.STUDENT) {
                        return new StudentDB(conn).queryStudent(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX));
                    }
                }

                return null;
            }
        }
    }

    /* Insert user.
    *
    * @param    user     user to insert.
    * @return   username of the inserted user.
     */

    public String insertUser(User user) throws SQLException {
        try (PreparedStatement insertUser = conn.prepareStatement(INSERT_USER)) {
            // Begin transaction.
            conn.setAutoCommit(false);

            insertUser.setString(COLUMN_USER_ID_INDEX, user.getUsername());
            insertUser.setString(COLUMN_USER_PASSWORD_INDEX, user.getPassword());
            insertUser.setString(COLUMN_USER_NAME_INDEX, user.getName());
            insertUser.setString(COLUMN_USER_TYPE_INDEX, user.getUserType().toString());

            if (insertUser.executeUpdate() != 1) {
                throw new SQLException("ERROR: Couldn't insert user with username: '" + user.getUsername() + "'.");
            }

            UserTypesEnum userType = user.getUserType();
            String username = null;

            if (userType == UserTypesEnum.PROFESSOR) {
                username = new ProfessorDB(conn).insertProfessor((Professor) user);
            } else if (userType == UserTypesEnum.STUDENT) {
                username = new StudentDB(conn).insertStudent((Student) user);
            } else if (userType == UserTypesEnum.ADMIN) {
                username = user.getUsername();
            }

            // End transaction.
            conn.setAutoCommit(true);

            return username;
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    /* Delete user.
    *
    * @param    id     username of the user to delete.
    * @return   Deleted user.
     */

    public User deleteUserByID(String id) throws SQLException {
        User foundUser = queryUserByID(id);

        if (foundUser == null) {
            throw new SQLException("ERROR: User with username: '" + id + "' doesn't exist.");
        }

        try (PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER)) {
            conn.setAutoCommit(false);

            if (foundUser.getUserType() == UserTypesEnum.PROFESSOR) {
                new ProfessorDB(conn).deleteProfessor(foundUser.getUsername(),
                        foundUser.getName(),
                        foundUser.getPassword());
            } else if (foundUser.getUserType() == UserTypesEnum.STUDENT) {
                new StudentDB(conn).deleteStudent(foundUser.getUsername(),
                        foundUser.getName(),
                        foundUser.getPassword());
            }

            deleteUser.setString(COLUMN_USER_ID_INDEX, id);

            if (deleteUser.executeUpdate() != 1) {
                throw new SQLException("ERROR: Couldn't delete user with username: '" + id + "'.");
            }

            conn.setAutoCommit(true);

            return foundUser;
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    /* Update user.
    *
    * @param    newUser     user to update.
    * @return   Updated user.
     */

    public User updateUser(User newUser) throws SQLException {
        User oldUser = queryUserByID(newUser.getUsername());

        if (oldUser == null) {
            throw new SQLException("ERROR: User with username: '" + newUser.getUsername() + "' doesn't exist.");
        }

        try (PreparedStatement updateUser = conn.prepareStatement(UPDATE_USER)) {
            conn.setAutoCommit(false);

            if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
                updateUser.setString(1, oldUser.getPassword());
            } else {
                updateUser.setString(1, newUser.getPassword());
            }

            if (newUser.getName() == null || newUser.getName().isEmpty()) {
                updateUser.setString(2, oldUser.getName());
            } else {
                updateUser.setString(2, newUser.getName());
            }

            updateUser.setString(3, oldUser.getUserType().toString());
            updateUser.setString(4, newUser.getUsername());

            if (updateUser.executeUpdate() != 1) {
                throw new SQLException("ERROR: Couldn't update user with username: '" + newUser.getUsername() + "'.");
            }

            if (newUser.getUserType() == UserTypesEnum.PROFESSOR) {
                new ProfessorDB(conn).updateProfessor((Professor) newUser);
            } else if (newUser.getUserType() == UserTypesEnum.STUDENT) {
                new StudentDB(conn).updateStudent((Student) newUser);
            }

            conn.setAutoCommit(true);

            return newUser;
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    /* Query all users.
    *
    * @return   List of all users.
     */

    public List<User> queryUsers() throws SQLException {
        try (PreparedStatement queryUsers = conn.prepareStatement(QUERY_USERS);
             ResultSet resultSet = queryUsers.executeQuery()) {
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                UserTypesEnum userType = UserTypesEnum.valueOf(resultSet.getString(COLUMN_USER_TYPE_INDEX));

                if (userType == UserTypesEnum.PROFESSOR) {
                    users.add(new ProfessorDB(conn).queryProfessor(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX)));

                } else if (userType == UserTypesEnum.ADMIN) {
                    users.add(new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                } else if (userType == UserTypesEnum.STUDENT) {
                    users.add(new StudentDB(conn).queryStudent(resultSet.getString(COLUMN_USER_ID_INDEX),
                            resultSet.getString(COLUMN_USER_NAME_INDEX),
                            resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                }
            }

            return users;
        }
    }

    /* Query users by username
    *
    * @param    id     username of the user to query.
    * @return   User with the given username.
     */

    public List<User> queryUsersByContainsID(String username) throws SQLException {
        try (PreparedStatement queryUsers = conn.prepareStatement(SEARCH_USERS_BY_CONTAINS_ID)) {
            queryUsers.setString(COLUMN_USER_ID_INDEX, "%" + username + "%");

            try (ResultSet resultSet = queryUsers.executeQuery()) {
                List<User> users = new ArrayList<>();

                while (resultSet.next()) {
                    UserTypesEnum userType = UserTypesEnum.valueOf(resultSet.getString(COLUMN_USER_TYPE_INDEX));

                    if (userType == UserTypesEnum.PROFESSOR) {
                        users.add(new ProfessorDB(conn).queryProfessor(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX)));

                    } else if (userType == UserTypesEnum.ADMIN) {
                        users.add(new Administrator(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                    } else if (userType == UserTypesEnum.STUDENT) {
                        users.add(new StudentDB(conn).queryStudent(resultSet.getString(COLUMN_USER_ID_INDEX),
                                resultSet.getString(COLUMN_USER_NAME_INDEX),
                                resultSet.getString(COLUMN_USER_PASSWORD_INDEX)));
                    }
                }

                return users;
            }
        }
    }

}