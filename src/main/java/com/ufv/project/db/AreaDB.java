package com.ufv.project.db;

import com.ufv.project.model.Field;

import java.sql.*;

public class AreaDB
{
    /*
     *   TB_Area table columns names
     */
    public static final String TABLE_AREA = "tb_Area";
    public static final String COLUMN_AREA_ID = "ID";
    public static final String COLUMN_AREA_NAME = "Name";

    public static final int COLUMN_AREA_ID_INDEX = 1;
    public static final int COLUMN_AREA_NAME_INDEX = 2;


    public static final String INSERT_AREA = "INSERT INTO " + TABLE_AREA + " (" + COLUMN_AREA_NAME + ") VALUES (?)";

    public static final String DELETE_AREA = "DELETE FROM " + TABLE_AREA + " WHERE " + COLUMN_AREA_ID + " = ?";

    public static final String GET_ALL_FIELDS = "SELECT * FROM " + TABLE_AREA;

    public static final String GET_AREA_NAME = "SELECT " + COLUMN_AREA_NAME + " FROM " + TABLE_AREA + " WHERE " + COLUMN_AREA_ID + " = ?";

    public static final String UPDATE_AREA_NAME = "UPDATE " + TABLE_AREA + " SET " + COLUMN_AREA_NAME + " = ? WHERE " + COLUMN_AREA_ID + " = ?";

    private PreparedStatement insertArea;
    private PreparedStatement updateField;
    private PreparedStatement deleteArea;
    private PreparedStatement getArea;

    private final Connection conn;

    public AreaDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            insertArea = conn.prepareStatement(INSERT_AREA, Statement.RETURN_GENERATED_KEYS);
            updateField = conn.prepareStatement(UPDATE_AREA_NAME);
            deleteArea = conn.prepareStatement(DELETE_AREA);
            getArea = conn.prepareStatement(GET_AREA_NAME);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String getAreaName(int id) throws SQLException
    {
        getArea.setInt(COLUMN_AREA_ID_INDEX, id);

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = getArea.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getString(COLUMN_AREA_NAME_INDEX);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

    public void setAreaName(int id, String name) throws SQLException
    {
        updateField.setInt(COLUMN_AREA_ID_INDEX, id);
        updateField.setString(COLUMN_AREA_NAME_INDEX, name);

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = updateField.executeQuery())
        {
            // Perform update.
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public int addArea(String name) throws SQLException
    {
        insertArea.setString(COLUMN_AREA_ID_INDEX, name);

        ResultSet resultSet = insertArea.executeQuery();

        if (resultSet.next())
        {
            return resultSet.getInt(COLUMN_AREA_ID_INDEX);
        }
        else
        {
            System.out.println("Error when inserting area.");
        }

        return -1;
    }

    public Field dropArea(int id) throws SQLException
    {
        deleteArea.setInt(COLUMN_AREA_NAME_INDEX, id);

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = deleteArea.executeQuery())
        {
            if (resultSet.next())
            {
                return new Field(resultSet.getInt(COLUMN_AREA_ID_INDEX), resultSet.getString(COLUMN_AREA_NAME_INDEX));
            }
            else
            {
                System.out.println("Error when deleting field.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

    public void printAreas()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_FIELDS))
        {
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(COLUMN_AREA_ID_INDEX) + "\t" +
                        resultSet.getString(COLUMN_AREA_NAME_INDEX));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void printAreaById(int id) throws SQLException
    {
        /*
         * This method prints an area in the database
         */
        System.out.println("ID: " + id
                + "\tName: " + getAreaName(id));
    }

}
