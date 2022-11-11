package com.ufv.project.db;

import com.ufv.project.model.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FieldDB {
    /*
     *   TB_Field table columns names
     */
    public static final String TABLE_FIELD = "tb_field";
    public static final String COLUMN_FIELD_ID = "ID";
    public static final String COLUMN_FIELD_NAME = "Name";

    public static final int COLUMN_FIELD_ID_INDEX = 1;
    public static final int COLUMN_FIELD_NAME_INDEX = 2;

    public static final String QUERY_FIELDS = "SELECT * FROM " + TABLE_FIELD;
    public static final String QUERY_FIELD = "SELECT * FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";

    public static final String INSERT_FIELD = "INSERT INTO " + TABLE_FIELD + " (" + COLUMN_FIELD_ID + ", " + COLUMN_FIELD_NAME + ") VALUES (?, ?)";

    public static final String DELETE_FIELD = "DELETE FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";

    public static final String UPDATE_FIELD_NAME = "UPDATE " + TABLE_FIELD + " SET " + COLUMN_FIELD_NAME + " = ? WHERE " + COLUMN_FIELD_ID + " = ?";

    private PreparedStatement queryField;

    private PreparedStatement queryFields;
    private PreparedStatement insertField;
    private PreparedStatement deleteField;
    private PreparedStatement updateField;

    private final Connection conn;

    public FieldDB(Connection conn) {
        /*
         * Initialize the connection

         */
        this.conn = conn;

        try {
            queryField = conn.prepareStatement(QUERY_FIELD);
            queryFields = conn.prepareStatement(QUERY_FIELDS);
            insertField = conn.prepareStatement(INSERT_FIELD, Statement.RETURN_GENERATED_KEYS);
            deleteField = conn.prepareStatement(DELETE_FIELD);
            updateField = conn.prepareStatement(UPDATE_FIELD_NAME);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Field queryFieldByID(int id) throws SQLException {

        queryField.setInt(COLUMN_FIELD_ID_INDEX, id);

        try (ResultSet resultSet = queryField.executeQuery()) {
            if (resultSet.next()) {
                return new Field(resultSet.getInt(COLUMN_FIELD_ID_INDEX), resultSet.getString(COLUMN_FIELD_NAME_INDEX));
            } else {
                return null;
            }
        }
    }

    public List<Field> queryFields() {
        try (ResultSet resultSet = queryFields.executeQuery()) {
            List<Field> fields = new ArrayList<>();
            while (resultSet.next()) {
                fields.add(new Field(resultSet.getInt(COLUMN_FIELD_ID_INDEX), resultSet.getString(COLUMN_FIELD_NAME_INDEX)));
            }
            return fields;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int insertField(Field field) throws SQLException {
        insertField.setInt(COLUMN_FIELD_ID_INDEX, field.getId());
        insertField.setString(COLUMN_FIELD_NAME_INDEX, field.getName());

        int affectedRows = insertField.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException("Couldn't insert field!");
        }

        try (ResultSet generatedKeys = insertField.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for field");
            }
        }
    }

    public Field deleteFieldByID(int id) throws SQLException {
        Field field = queryFieldByID(id);
        deleteField.setInt(1, id);
        deleteField.executeUpdate();
        return field;
    }

    public Field updateField(Field new_field) throws SQLException {
        //Returns the old field
        Field oldField = queryFieldByID(new_field.getId());
        updateField.setString(1, new_field.getName());
        updateField.setInt(2, new_field.getId());
        updateField.executeUpdate();
        return oldField;
    }

    void close() {
        try {
            if (queryField != null) {
                queryField.close();
            }
            if (queryFields != null) {
                queryFields.close();
            }
            if (insertField != null) {
                insertField.close();
            }
            if (deleteField != null) {
                deleteField.close();
            }
            if (updateField != null) {
                updateField.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}