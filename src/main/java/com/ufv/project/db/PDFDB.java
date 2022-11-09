package com.ufv.project.db;

import com.ufv.project.model.Field;
import com.ufv.project.model.PDF;

import java.io.File;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PDFDB
{
    /*
     * TB_PDF table columns names
     */

    public static final String TABLE_PDF = "TB_PDF";
    public static final String COLUMN_PDF_ID = "ID";
    public static final String COLUMN_PDF_CONTENT = "Content";
    public static final String COLUMN_PDF_CREATION_DATE = "Creation_Date";

    public static final int COLUMN_PDF_ID_INDEX = 1;
    public static final int COLUMN_PDF_CONTENT_INDEX = 2;
    public static final int COLUMN_PDF_CREATION_DATE_INDEX = 3;

    public static final String GET_PDF = "SELECT " + COLUMN_PDF_CONTENT + " FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";

    public static final String GET_PDF_CREATION_DATE = "SELECT " + COLUMN_PDF_CREATION_DATE + " FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";

    public static final String SET_PDF_CONTENT = "UPDATE " + TABLE_PDF + " SET " + COLUMN_PDF_CONTENT + " = ? WHERE " + COLUMN_PDF_ID + " = ?";

    public static final String SET_PDF_CREATION_DATE = "UPDATE " + TABLE_PDF + " SET " + COLUMN_PDF_CREATION_DATE + " = ? WHERE " + COLUMN_PDF_ID + " = ?";

    public static final String INSERT_PDF = "INSERT INTO " + TABLE_PDF + " (" + COLUMN_PDF_ID + ", " + COLUMN_PDF_CREATION_DATE + ", " + COLUMN_PDF_CONTENT + ") VALUES (?, ?, ?)";

    public static final String DELETE_PDF = "DELETE FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";

    public static final String GET_ALL_PDFS = "SELECT * FROM " + TABLE_PDF;

    private PreparedStatement getPDF;
    private PreparedStatement insertPDF;
    private PreparedStatement updatePDF;
    private PreparedStatement deletePDF;


    private Connection conn;

    public PDFDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getPDF = conn.prepareStatement(GET_PDF);
            insertPDF = conn.prepareStatement(INSERT_PDF, Statement.RETURN_GENERATED_KEYS);
            deletePDF = conn.prepareStatement(DELETE_PDF);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public PDF getPDFByID(int id) throws SQLException
    {
        getPDF.setInt(COLUMN_PDF_ID_INDEX, id);

        try (ResultSet resultSet = getPDF.executeQuery())
        {
            if (resultSet.next())
            {
                return new PDF(resultSet.getInt(COLUMN_PDF_ID_INDEX),
                        new File(resultSet.getString(COLUMN_PDF_CONTENT_INDEX)),
                        resultSet.getDate(COLUMN_PDF_CREATION_DATE_INDEX).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int insertPDF(PDF pdfToInsert) throws SQLException
    {
        insertPDF.setInt(COLUMN_PDF_ID_INDEX, pdfToInsert.getId());

        try (ResultSet resultSet = insertPDF.executeQuery())
        {
            if (resultSet.next())
            {
                return resultSet.getInt(COLUMN_PDF_ID_INDEX);
            }
            else
            {
                System.out.println("Error when inserting PDF.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    public PDF deletePDF(int idToDelete) throws SQLException
    {
        deletePDF.setInt(COLUMN_PDF_ID_INDEX, idToDelete);

        try (ResultSet resultSet = deletePDF.executeQuery())
        {
            if (resultSet.next())
            {
                return new PDF(resultSet.getInt(COLUMN_PDF_ID_INDEX),
                        new File(resultSet.getString(COLUMN_PDF_CONTENT_INDEX)),
                        resultSet.getDate(COLUMN_PDF_CREATION_DATE_INDEX).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            else
            {
                System.out.println("Error when deleting PDF.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<PDF> getAllPDFs()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PDFS))
        {
            List<PDF> pdfs = new ArrayList<>();

            while (resultSet.next())
            {
                pdfs.add(new PDF(resultSet.getInt(COLUMN_PDF_ID_INDEX),
                        new File(resultSet.getString(COLUMN_PDF_CONTENT_INDEX)),
                        resultSet.getDate(COLUMN_PDF_CREATION_DATE_INDEX).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            }

            return pdfs;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

}
