package com.ufv.project.db;

import com.ufv.project.model.PDF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PDFDB
{
    /*  Table PDF constants. */

    private static final String TABLE_PDF = "TB_PDF";
    private static final String COLUMN_PDF_ID = "ID";
    private static final String COLUMN_PDF_PATH = "Path";
    private static final String COLUMN_PDF_CREATION_DATE = "Creation_Date";

    /* Table PDF constants indexes. */

    private static final int COLUMN_PDF_ID_INDEX = 1;
    private static final int COLUMN_PDF_CREATION_DATE_INDEX = 2;
    private static final int COLUMN_PDF_PATH_INDEX = 3;

    /* Table PDF queries. */


    private static final String QUERY_PDF = "SELECT * FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";
    private static final String QUERY_PDFS = "SELECT * FROM " + TABLE_PDF;
    private static final String INSERT_PDF = "INSERT INTO " + TABLE_PDF + " (" + COLUMN_PDF_ID + ", " + COLUMN_PDF_CREATION_DATE + ", " + COLUMN_PDF_PATH + ") VALUES (?, ?, ?)";
    private static final String UPDATE_PDF = "UPDATE " + TABLE_PDF + " SET " + COLUMN_PDF_PATH + " = ?, " + COLUMN_PDF_CREATION_DATE + " = ? WHERE " + COLUMN_PDF_ID + " = ?";
    private static final String DELETE_PDF = "DELETE FROM " + TABLE_PDF + " WHERE " + COLUMN_PDF_ID + " = ?";

    /* Connection to the database. */
    private final Connection conn;

    public PDFDB(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Query a PDF by its ID.
     *
     * @param id ID of the PDF to query.
     * @return PDF with the given ID.
     * @throws SQLException if the query fails.
     */

    public PDF queryPDFByID(int id) throws SQLException
    {
        try (PreparedStatement queryPDF = conn.prepareStatement(QUERY_PDF))
        {
            queryPDF.setInt(1, id);

            try (ResultSet results = queryPDF.executeQuery())
            {
                if (results.next())
                {
                    return new PDF(results.getInt(COLUMN_PDF_ID_INDEX), results.getString(COLUMN_PDF_PATH_INDEX), results.getDate(COLUMN_PDF_CREATION_DATE_INDEX).toLocalDate());
                }

                return null;
            }
        }
    }

    /**
     * Query all PDFs from the database.
     *
     * @return a list with all PDFs.
     * @throws SQLException if the query fails.
     */

    public List<PDF> queryPDFs() throws SQLException
    {
        try (PreparedStatement queryPDFs = conn.prepareStatement(QUERY_PDFS);
             ResultSet results = queryPDFs.executeQuery())
        {
            List<PDF> pdfs = new ArrayList<>();

            while (results.next())
            {
                pdfs.add(new PDF(results.getInt(COLUMN_PDF_ID_INDEX), results.getString(COLUMN_PDF_PATH_INDEX), results.getDate(COLUMN_PDF_CREATION_DATE_INDEX).toLocalDate()));
            }

            return pdfs;
        }
    }

    /**
     * Insert a new PDF into the database.
     *
     * @param pdfToInsert PDF to insert.
     * @return inserted PDF.
     * @throws SQLException if the insertion fails.
     */

    public int insertPDF(PDF pdfToInsert) throws SQLException
    {
        try (PreparedStatement insertPDF = conn.prepareStatement(INSERT_PDF, Statement.RETURN_GENERATED_KEYS))
        {
            insertPDF.setInt(COLUMN_PDF_ID_INDEX, pdfToInsert.getId());
            insertPDF.setDate(COLUMN_PDF_CREATION_DATE_INDEX, Date.valueOf(pdfToInsert.getLastModificationDate()));
            insertPDF.setString(COLUMN_PDF_PATH_INDEX, pdfToInsert.getPath());

            if (insertPDF.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert PDF from: '" + pdfToInsert.getId() + "'.");
            }

            try (ResultSet generatedKeys = insertPDF.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    return generatedKeys.getInt(COLUMN_PDF_ID_INDEX);
                }
            }

            throw new SQLException("ERROR: Couldn't get _id for PDF.");
        }
    }

    /**
     * Delete a PDF from the database.
     *
     * @param pdfToDelete PDF to delete.
     * @return deleted PDF.
     * @throws SQLException if the deletion fails.
     */

    public PDF deletePDF(PDF pdfToDelete) throws SQLException
    {
        try (PreparedStatement deletePDF = conn.prepareStatement(DELETE_PDF))
        {
            deletePDF.setInt(COLUMN_PDF_ID_INDEX, pdfToDelete.getId());

            if (deletePDF.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete PDF with ID: '" + pdfToDelete.getId() + "'.");
            }

            return pdfToDelete;
        }
    }

    /**
     * Update a PDF in the database.
     *
     * @param newPDF PDF to update.
     * @return updated PDF.
     * @throws SQLException if the update fails.
     */

    public PDF updatePDF(PDF newPDF) throws SQLException
    {
        PDF oldPDF = queryPDFByID(newPDF.getId());

        if (oldPDF == null)
        {
            throw new SQLException("ERROR: PDF with ID: '" + newPDF.getId() + "' doesn't exists.");
        }

        try (PreparedStatement updatePDF = conn.prepareStatement(UPDATE_PDF))
        {
            if (newPDF.getPath() != null)
            {
                updatePDF.setString(1, newPDF.getPath());
            }
            else
            {
                updatePDF.setString(1, oldPDF.getPath());
            }

            if (newPDF.getLastModificationDate() != null)
            {
                updatePDF.setDate(2, Date.valueOf(newPDF.getLastModificationDate()));
            }
            else
            {
                updatePDF.setDate(2, Date.valueOf(oldPDF.getLastModificationDate()));
            }

            updatePDF.setInt(3, newPDF.getId());

            if (updatePDF.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update PDF with ID: '" + newPDF.getId() + "'.");
            }

            return newPDF;
        }
    }

}

