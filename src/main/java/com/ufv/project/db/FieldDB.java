package com.ufv.project.db;

import com.ufv.project.model.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FieldDB
{
    /*
     *   TB_Field table columns names
     */
    public static final String TABLE_FIELD = "tb_area";
    public static final String COLUMN_FIELD_ID = "ID";
    public static final String COLUMN_FIELD_NAME = "Name";

    public static final int COLUMN_FIELD_ID_INDEX = 1;
    public static final int COLUMN_FIELD_NAME_INDEX = 2;


    public static final String INSERT_FIELD = "INSERT INTO " + TABLE_FIELD + " (" + COLUMN_FIELD_NAME + ") VALUES (?)";

    public static final String DELETE_FIELD = "DELETE FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";

    public static final String GET_ALL_FIELDS = "SELECT * FROM " + TABLE_FIELD;

    public static final String GET_FIELD_NAME = "SELECT " + COLUMN_FIELD_NAME + " FROM " + TABLE_FIELD + " WHERE " + COLUMN_FIELD_ID + " = ?";

    public static final String UPDATE_FIELD_NAME = "UPDATE " + TABLE_FIELD + " SET " + COLUMN_FIELD_NAME + " = ? WHERE " + COLUMN_FIELD_ID + " = ?";

    private PreparedStatement insertField;
    private PreparedStatement updateField;
    private PreparedStatement deleteField;
    private PreparedStatement getField;

    private final Connection conn;

    public FieldDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            insertField = conn.prepareStatement(INSERT_FIELD, Statement.RETURN_GENERATED_KEYS);
            updateField = conn.prepareStatement(UPDATE_FIELD_NAME);
            deleteField = conn.prepareStatement(DELETE_FIELD);
            getField = conn.prepareStatement(GET_FIELD_NAME);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Field getFieldByID(int id) throws SQLException
    {
        getField.setInt(COLUMN_FIELD_ID_INDEX, id);

        try (ResultSet resultSet = getField.executeQuery())
        {
            if (resultSet.next())
            {
                return new Field(resultSet.getInt(COLUMN_FIELD_ID), resultSet.getString(COLUMN_FIELD_NAME_INDEX));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public int insertField(Field field) throws SQLException
    {
        insertField.setString(COLUMN_FIELD_ID_INDEX, field.getName());

        System.out.println(field.getName());

        try (ResultSet resultSet = insertField.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getInt(COLUMN_FIELD_ID_INDEX);
            }
            else
            {
                System.out.println("Error when inserting area.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    public Field deleteFieldByID(int id) throws SQLException
    {
        deleteField.setInt(COLUMN_FIELD_NAME_INDEX, id);

        try (ResultSet resultSet = deleteField.executeQuery())
        {
            if (resultSet.next())
            {
                return new Field(resultSet.getInt(COLUMN_FIELD_ID_INDEX), resultSet.getString(COLUMN_FIELD_NAME_INDEX));
            }
            else
            {
                System.out.println("Error when deleting field.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Field> getAllFields()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_FIELDS))
        {
            List<Field> fields = new ArrayList<>();

            while (resultSet.next())
            {
                fields.add(new Field(resultSet.getInt(COLUMN_FIELD_ID), resultSet.getString(COLUMN_FIELD_NAME_INDEX)));
            }

            return fields;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

}