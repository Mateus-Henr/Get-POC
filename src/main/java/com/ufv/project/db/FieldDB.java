package com.ufv.project.db;

import com.ufv.project.model.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FieldDB
{
    /*
     *   TB_Field table column's names
     */
    private static final String TABLE_FIELD = "tb_field";
    private static final String COLUMN_FIELD_ID = "ID";
    private static final String COLUMN_FIELD_NAME = "Name";

    private static final int COLUMN_FIELD_ID_INDEX = 1;
    private static final int COLUMN_FIELD_NAME_INDEX = 2;

    private static final String QUERY_FIELD = "SELECT * FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";
    private static final String QUERY_FIELDS = "SELECT * FROM " + TABLE_FIELD;
    private static final String INSERT_FIELD = "INSERT INTO " + TABLE_FIELD + " (" + COLUMN_FIELD_ID + ", " + COLUMN_FIELD_NAME + ") VALUES (?, ?)";
    private static final String UPDATE_FIELD_NAME = "UPDATE " + TABLE_FIELD + " SET " + COLUMN_FIELD_NAME + " = ? WHERE " + COLUMN_FIELD_ID + " = ?";
    private static final String DELETE_FIELD = "DELETE FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";

    private final Connection conn;

    public FieldDB(Connection conn) throws SQLException
    {
        this.conn = conn;
    }

    public Field queryFieldByID(int id) throws SQLException
    {
        try (PreparedStatement queryField = conn.prepareStatement(QUERY_FIELD);)
        {
            queryField.setInt(COLUMN_FIELD_ID_INDEX, id);

            try (ResultSet resultSet = queryField.executeQuery())
            {
                if (resultSet.next())
                {
                    return new Field(resultSet.getInt(COLUMN_FIELD_ID_INDEX), resultSet.getString(COLUMN_FIELD_NAME_INDEX));
                }

                return null;
            }
        }
    }

    public List<Field> queryFields() throws SQLException
    {
        try (PreparedStatement queryFields = conn.prepareStatement(QUERY_FIELDS);
             ResultSet resultSet = queryFields.executeQuery())
        {
            List<Field> fields = new ArrayList<>();

            while (resultSet.next())
            {
                fields.add(new Field(resultSet.getInt(COLUMN_FIELD_ID_INDEX), resultSet.getString(COLUMN_FIELD_NAME_INDEX)));
            }

            return fields;
        }
    }

    public int insertField(Field field) throws SQLException
    {
        try (PreparedStatement insertField = conn.prepareStatement(INSERT_FIELD, Statement.RETURN_GENERATED_KEYS))
        {
            insertField.setInt(COLUMN_FIELD_ID_INDEX, field.getId());
            insertField.setString(COLUMN_FIELD_NAME_INDEX, field.getName());

            int affectedRows = insertField.executeUpdate();

            if (affectedRows != 1)
            {
                throw new SQLException("Couldn't insert field!");
            }

            try (ResultSet generatedKeys = insertField.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    return generatedKeys.getInt(1);
                }
            }

            throw new SQLException("Couldn't get _id for field.");
        }
    }

    public Field deleteFieldByID(int id) throws SQLException
    {
        try (PreparedStatement deleteField = conn.prepareStatement(DELETE_FIELD))
        {
            Field field = queryFieldByID(id);

            if (field == null)
            {
                throw new SQLException("Field with ID: " + id + " doesn't exist.");
            }

            deleteField.setInt(1, id);
            deleteField.executeUpdate();

            return field;
        }
    }

    public Field updateField(Field newField) throws SQLException
    {
        Field oldField = queryFieldByID(newField.getId());

        if (oldField == null)
        {
            throw new SQLException("Field with ID " + newField + " doesn't exist.");
        }

        try (PreparedStatement updateField = conn.prepareStatement(UPDATE_FIELD_NAME))
        {
            updateField.setInt(2, newField.getId());

            if (newField.getName() == null)
            {
                updateField.setString(1, oldField.getName());
            }
            else
            {
                updateField.setString(1, newField.getName());
            }

            updateField.executeUpdate();
        }

        return oldField;
    }

}