package com.ufv.project.db;

import com.ufv.project.model.Field;
import com.ufv.project.model.PDF;
import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class POCDB
{
    /*
     * TB_POC table columns names
     */
    private static final String TABLE_POC = "TB_POC";
    private static final String COLUMN_POC_ID = "ID";
    private static final String COLUMN_POC_TITLE = "Title";
    private static final String COLUMN_POC_DEFENSE_DATE = "Defense_Date";
    private static final String COLUMN_POC_SUMMARY = "Summary";
    private static final String COLUMN_POC_AREA_ID = "TB_Area_ID";
    private static final String COLUMN_POC_PDF_ID = "TB_PDF_ID";
    private static final String COLUMN_POC_TEACHER_REGISTRANT_ID = "Teacher_Registrant";
    private static final String COLUMN_POC_TEACHER_ADVISOR_ID = "Teacher_Advisor";

    private static final int COLUMN_POC_ID_INDEX = 1;
    private static final int COLUMN_POC_TITLE_INDEX = 2;
    private static final int COLUMN_POC_AUTHORS_ID_INDEX = 3;
    private static final int COLUMN_POC_DEFENSE_DATE_INDEX = 4;
    private static final int COLUMN_POC_SUMMARY_INDEX = 5;
    private static final int COLUMN_POC_FIELD_ID_INDEX = 6;
    private static final int COLUMN_POC_PDF_ID_INDEX = 7;
    private static final int COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX = 8;
    private static final int COLUMN_POC_TEACHER_ADVISOR_ID_INDEX = 9;

    private static final String GET_POC = "SELECT * " +
            "FROM " + TABLE_POC + " " +
            "WHERE " + COLUMN_POC_ID + " = ?";

    private static final String INSERT_POC = "INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_AREA_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ALL_POCS = "SELECT * FROM " + TABLE_POC;

    private static final String DELETE_POC = "DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    private static final String SEARCH_POC_BY_TEXT = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_TITLE + " LIKE '%?%'";

    private PreparedStatement getPOC;
    private PreparedStatement insertPOC;
    private PreparedStatement updatePOC;
    private PreparedStatement deletePOC;
    private PreparedStatement searchPOC;

    private Connection conn;

    public POCDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getPOC = conn.prepareStatement(GET_POC);
            insertPOC = conn.prepareStatement(INSERT_POC);
//            deletePOC = conn.prepareStatement(DELETE_POC);
            searchPOC = conn.prepareStatement(SEARCH_POC_BY_TEXT);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public POC getPOCByID(int id) throws SQLException
    {
        getPOC.setInt(COLUMN_POC_ID_INDEX, id);

        ResultSet resultSet = getPOC.executeQuery();

        if (resultSet.next())
        {
            return new POC.POCBuilder()
                    .title(resultSet.getString(COLUMN_POC_TITLE_INDEX))
                    .defenseDate(resultSet.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate())
                    .advisor(getPOCAdvisorByPOCID(id))
                    .coAdvisors(getPOCCoAdvisorByPOCID(id))
                    .registrant(getPOCRegistrantByPOCID(id))
                    .pdf(getPOCPDFByPOCID(id))
                    .field(getPOCFieldByPOCID(id))
                    .summary(resultSet.getString(COLUMN_POC_SUMMARY_INDEX))
                    .keywords(getPOCKeywordsByPOCID(id))
                    .build();
        }

        return null;
    }

    public Professor getPOCAdvisorByPOCID(int id) throws SQLException
    {
        return null;
    }

    public List<Professor> getPOCCoAdvisorByPOCID(int id) throws SQLException
    {
        return null;
    }

    public Professor getPOCRegistrantByPOCID(int id) throws SQLException
    {
        return null;
    }

    public PDF getPOCPDFByPOCID(int id) throws SQLException
    {
        return null;
    }

    public Field getPOCFieldByPOCID(int id) throws SQLException
    {
        return null;
    }

    public List<String> getPOCKeywordsByPOCID(int id) throws SQLException
    {
        return null;
    }


    /*
     * Methods
     */


    public int insertPOC(POC poc) throws SQLException
    {
        insertPOC.setInt(COLUMN_POC_ID_INDEX, poc.getId());
        insertPOC.setString(COLUMN_POC_TITLE_INDEX, poc.getTitle());
        // Add the rest.

        ResultSet resultSet = insertPOC.executeQuery();

        if (resultSet.next())
        {
            return resultSet.getInt(COLUMN_POC_ID);
        }
        else
        {
            System.out.println("Error when inserting area.");
        }

        return -1;
    }

    public POC deletePOCByID(int id) throws SQLException
    {
        deletePOC.setInt(COLUMN_POC_ID_INDEX, id);

        ResultSet resultSet = deletePOC.executeQuery();

        if (resultSet.next())
        {
            return new POC.POCBuilder()
                    .title(resultSet.getString(COLUMN_POC_TITLE_INDEX))
                    .defenseDate(resultSet.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate())
                    .advisor(getPOCAdvisorByPOCID(id))
                    .coAdvisors(getPOCCoAdvisorByPOCID(id))
                    .registrant(getPOCRegistrantByPOCID(id))
                    .pdf(getPOCPDFByPOCID(id))
                    .field(getPOCFieldByPOCID(id))
                    .summary(resultSet.getString(COLUMN_POC_SUMMARY_INDEX))
                    .keywords(getPOCKeywordsByPOCID(id))
                    .build();
        }
        else
        {
            System.out.println("Error when deleting field.");
        }

        return null;
    }

    public List<POC> getAllPOs()
    {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_POCS))
        {
            List<POC> pocs = new ArrayList<>();

            while (resultSet.next())
            {
                int pocId = resultSet.getInt(COLUMN_POC_ID_INDEX);

                pocs.add(new POC.POCBuilder()
                        .title(resultSet.getString(COLUMN_POC_TITLE_INDEX))
                        .defenseDate(resultSet.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate())
                        .advisor(getPOCAdvisorByPOCID(pocId))
                        .coAdvisors(getPOCCoAdvisorByPOCID(pocId))
                        .registrant(getPOCRegistrantByPOCID(pocId))
                        .pdf(getPOCPDFByPOCID(pocId))
                        .field(getPOCFieldByPOCID(pocId))
                        .summary(resultSet.getString(COLUMN_POC_SUMMARY_INDEX))
                        .keywords(getPOCKeywordsByPOCID(pocId))
                        .build());
            }

            return pocs;
        }
        catch (SQLException e)
        {
            System.out.println("Query failed: " + e.getMessage());
        }

        return null;
    }

    public List<POC> searchPocByTextInTitle(String text) throws SQLException
    {
        searchPOC.setString(1, text);

        try (ResultSet resultSet = searchPOC.executeQuery())
        {
            List<POC> pocs = new ArrayList<>();

            while (resultSet.next())
            {
                int pocId = resultSet.getInt(COLUMN_POC_ID_INDEX);

                pocs.add(new POC.POCBuilder()
                        .title(resultSet.getString(COLUMN_POC_TITLE_INDEX))
                        .defenseDate(resultSet.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate())
                        .advisor(getPOCAdvisorByPOCID(pocId))
                        .coAdvisors(getPOCCoAdvisorByPOCID(pocId))
                        .registrant(getPOCRegistrantByPOCID(pocId))
                        .pdf(getPOCPDFByPOCID(pocId))
                        .field(getPOCFieldByPOCID(pocId))
                        .summary(resultSet.getString(COLUMN_POC_SUMMARY_INDEX))
                        .keywords(getPOCKeywordsByPOCID(pocId))
                        .build());
            }

            return pocs;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
