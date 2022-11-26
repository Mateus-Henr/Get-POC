package com.ufv.project.db;

import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;
import com.ufv.project.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ufv.project.db.StudentDB.*;

public class POCDB
{
    /*TB_POC table columns names*/

    private static final String TABLE_POC = "TB_POC";
    private static final String COLUMN_POC_ID = "ID";
    private static final String COLUMN_POC_TITLE = "Title";
    private static final String COLUMN_POC_DEFENSE_DATE = "Defense_Date";
    private static final String COLUMN_POC_SUMMARY = "Summary";
    private static final String COLUMN_POC_FIELD_ID = "TB_Area_ID";
    private static final String COLUMN_POC_PDF_ID = "TB_PDF_ID";
    private static final String COLUMN_POC_TEACHER_REGISTRANT_ID = "Teacher_Registrant";
    private static final String COLUMN_POC_TEACHER_ADVISOR_ID = "Teacher_Advisor";

    private static final int COLUMN_POC_ID_INDEX = 1;
    private static final int COLUMN_POC_TITLE_INDEX = 2;
    private static final int COLUMN_POC_DEFENSE_DATE_INDEX = 3;
    private static final int COLUMN_POC_SUMMARY_INDEX = 4;
    private static final int COLUMN_POC_FIELD_INDEX = 5;
    private static final int COLUMN_POC_PDF_ID_INDEX = 6;
    private static final int COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX = 7;
    private static final int COLUMN_POC_TEACHER_ADVISOR_ID_INDEX = 8;

    private static final String QUERY_POC = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";
    private static final String QUERY_POCs = "SELECT * FROM " + TABLE_POC;
    private static final String INSERT_POC = "INSERT INTO " + TABLE_POC + " (" + COLUMN_POC_ID + ", " + COLUMN_POC_TITLE + ", " + COLUMN_POC_DEFENSE_DATE + ", " + COLUMN_POC_SUMMARY + ", " + COLUMN_POC_FIELD_ID + ", " + COLUMN_POC_PDF_ID + ", " + COLUMN_POC_TEACHER_REGISTRANT_ID + ", " + COLUMN_POC_TEACHER_ADVISOR_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_POC = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TITLE + " = ?, " + COLUMN_POC_DEFENSE_DATE + " = ?, " + COLUMN_POC_SUMMARY + " = ?, " + COLUMN_POC_FIELD_ID + " = ?, " + COLUMN_POC_PDF_ID + " = ?, " + COLUMN_POC_TEACHER_REGISTRANT_ID + " = ?, " + COLUMN_POC_TEACHER_ADVISOR_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";
    private static final String DELETE_POC = "DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";
    private static final String SEARCH_POC_BY_TEXT_TITLE = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_TITLE + " LIKE ?";
    private static final String SEARCH_POC_BY_TEXT_SUMMARY = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_SUMMARY + " LIKE ?";
    private static final String SEARCH_POC_BY_STUDENT = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " IN (SELECT " + COLUMN_STUDENT_POC + " FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " LIKE ?)";
    private static final String SEARCH_POC_BY_ADVISOR = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_TEACHER_ADVISOR_ID + " LIKE ?";
    private static final String SEARCH_POC_BY_FIELD = "SELECT * FROM " + TABLE_POC + " WHERE " + COLUMN_POC_FIELD_ID + " LIKE ?";
    private final Connection conn;

    public POCDB(Connection conn)
    {
        this.conn = conn;
    }

    public POC queryPOC(int id) throws SQLException
    {
        try (PreparedStatement queryPOC = conn.prepareStatement(QUERY_POC))
        {
            queryPOC.setInt(1, id);

            try (ResultSet results = queryPOC.executeQuery())
            {
                if (results.next())
                {
                    POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
                    Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
                    StudentDB studentDB = new StudentDB(conn);
                    FieldDB fieldDB = new FieldDB(conn);
                    PDFDB pdfDB = new PDFDB(conn);
                    UserDB userDB = new UserDB(conn);

                    return new POC(
                            results.getInt(COLUMN_POC_ID_INDEX),
                            results.getString(COLUMN_POC_TITLE_INDEX),
                            studentDB.queryStudentsByPocID(id),
                            results.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate(),
                            poc_has_keywordDB.queryKeywordsByPOCID(id),
                            results.getString(COLUMN_POC_SUMMARY_INDEX),
                            fieldDB.queryFieldByID(results.getInt(COLUMN_POC_FIELD_INDEX)),
                            pdfDB.queryPDFByID(results.getInt(COLUMN_POC_PDF_ID_INDEX)),
                            (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX)),
                            (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_ADVISOR_ID_INDEX)),
                            professor_co_advises_pocDB.queryProfessorsByPocId(id));
                }

                return null;
            }
        }
    }

    public int insertPOC(POC poc) throws SQLException
    {
        try (PreparedStatement insertPOC = conn.prepareStatement(INSERT_POC, Statement.RETURN_GENERATED_KEYS))
        {
            conn.setAutoCommit(false);

            insertPOC.setInt(1, poc.getId());
            insertPOC.setString(2, poc.getTitle());
            insertPOC.setString(3, Date.valueOf(poc.getDefenseDate()).toString());
            insertPOC.setString(4, poc.getSummary());
            insertPOC.setInt(5, poc.getField().getId());
            insertPOC.setInt(6, new PDFDB(conn).insertPDF(poc.getPdf()));
            insertPOC.setString(7, poc.getRegistrant().getUsername());
            insertPOC.setString(8, poc.getAdvisor().getUsername());

            if (insertPOC.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert POC with ID: '" + poc.getId() + "'.");
            }

            try (ResultSet resultSet = insertPOC.getGeneratedKeys())
            {
                if (resultSet.next())
                {
                    int POCID = resultSet.getInt(COLUMN_POC_ID_INDEX);

                    Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);

                    for (Professor coAdvisor : poc.getCoAdvisors())
                    {
                        professor_co_advises_pocDB.insertProfessor_co_advises_poc(coAdvisor.getUsername(), POCID);
                    }

                    KeywordDB keywordDB = new KeywordDB(conn);
                    POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);

                    for (String keyword : poc.getKeywords())
                    {
                        keywordDB.insertKeyword(keyword);
                        poc_has_keywordDB.insertPOC_has_Keyword(POCID, keyword);
                    }

                    StudentDB studentDB = new StudentDB(conn);

                    for (Student student : poc.getAuthors())
                    {
                        studentDB.setStudentPOC(student.getUsername(), POCID);
                    }

                    conn.setAutoCommit(true);

                    return POCID;
                }

                throw new SQLException("ERROR: Couldn't get _id for POC.");
            }
        }
        catch (SQLException e)
        {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    public POC deletePOC(int id) throws SQLException
    {
        POC foundPOC = queryPOC(id);

        if (foundPOC == null)
        {
            throw new SQLException("ERROR: POC with ID: '" + id + "' doesn't exist.");
        }

        try (PreparedStatement deletePOC = conn.prepareStatement(DELETE_POC))
        {
            conn.setAutoCommit(false);

            POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
            poc_has_keywordDB.deletePOC_has_Keyword(id);

            Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
            professor_co_advises_pocDB.deleteProfessor_co_advises_poc(id);

            StudentDB studentDB = new StudentDB(conn);
            studentDB.setStudentPOCNull(id);

            deletePOC.setInt(COLUMN_POC_ID_INDEX, id);

            if (deletePOC.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete POC with ID: '" + id + "'.");
            }

            conn.setAutoCommit(true);

            return foundPOC;
        }
        catch (SQLException e)
        {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    public POC updatePOC(POC poc) throws SQLException
    {
        POC oldPOC = queryPOC(poc.getId());

        if (oldPOC == null)
        {
            throw new SQLException("ERROR: Couldn't find POC with ID: '" + poc.getId() + "'.");
        }

        try (PreparedStatement updatePOC = conn.prepareStatement(UPDATE_POC))
        {
            conn.setAutoCommit(false);

            // Update Keywords
            POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
            poc_has_keywordDB.deletePOC_has_Keyword(poc.getId());

            for (String keyword : poc.getKeywords())
            {
                poc_has_keywordDB.insertPOC_has_Keyword(poc.getId(), keyword);
            }

            // Update Co-Advisors
            Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
            professor_co_advises_pocDB.deleteProfessor_co_advises_poc(poc.getId());

            for (Professor professor : poc.getCoAdvisors())
            {
                professor_co_advises_pocDB.insertProfessor_co_advises_poc(professor.getUsername(), poc.getId());
            }

            // Update Authors
            StudentDB studentDB = new StudentDB(conn);
            studentDB.setStudentPOCNull(poc.getId());

            for (Student student : poc.getAuthors())
            {
                studentDB.setStudentPOC(student.getUsername(), poc.getId());
            }

            if (poc.getTitle() != null && !poc.getTitle().isEmpty())
            {
                updatePOC.setString(1, poc.getTitle());
            }
            else
            {
                updatePOC.setString(1, oldPOC.getTitle());
            }

            if (poc.getDefenseDate() != null)
            {
                updatePOC.setString(2, Date.valueOf(poc.getDefenseDate()).toString());
            }
            else
            {
                updatePOC.setString(2, Date.valueOf(oldPOC.getDefenseDate()).toString());
            }

            if (poc.getSummary() != null && !poc.getSummary().isEmpty())
            {
                updatePOC.setString(3, poc.getSummary());
            }
            else
            {
                updatePOC.setString(3, oldPOC.getSummary());
            }

            if (poc.getField() != null)
            {
                updatePOC.setInt(4, poc.getField().getId());
            }
            else
            {
                updatePOC.setInt(4, oldPOC.getField().getId());
            }

            if (poc.getPdf() != null)
            {
                updatePOC.setInt(5, poc.getPdf().getId());
            }
            else
            {
                updatePOC.setInt(5, oldPOC.getPdf().getId());
            }

            if (poc.getRegistrant() != null)
            {
                updatePOC.setString(6, poc.getRegistrant().getUsername());
            }
            else
            {
                updatePOC.setString(6, oldPOC.getRegistrant().getUsername());
            }

            if (poc.getAdvisor() != null)
            {
                updatePOC.setString(7, poc.getAdvisor().getUsername());
            }
            else
            {
                updatePOC.setString(7, oldPOC.getAdvisor().getUsername());
            }

            updatePOC.setInt(8, poc.getId());

            if (updatePOC.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update POC with ID: '" + poc.getId() + "'.");
            }

            conn.setAutoCommit(true);

            return poc;
        }
        catch (SQLException e)
        {
            conn.rollback();
            conn.setAutoCommit(true);

            throw new SQLException(e);
        }
    }

    public List<POC> queryAllPOCs() throws SQLException
    {
        try (PreparedStatement queryPOCs = conn.prepareStatement(QUERY_POCs);
             ResultSet results = queryPOCs.executeQuery())
        {
            List<POC> pocs = new ArrayList<>();
            StudentDB studentDB = new StudentDB(conn);
            FieldDB fieldDB = new FieldDB(conn);
            PDFDB pdfDB = new PDFDB(conn);
            UserDB userDB = new UserDB(conn);
            Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
            POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);

            while (results.next())
            {
                pocs.add(new POC(results.getInt(COLUMN_POC_ID),
                        results.getString(COLUMN_POC_TITLE),
                        studentDB.queryStudentsByPocID(results.getInt(COLUMN_POC_ID)),
                        results.getDate(COLUMN_POC_DEFENSE_DATE).toLocalDate(),
                        poc_has_keywordDB.queryKeywordsByPOCID(results.getInt(COLUMN_POC_ID)),
                        results.getString(COLUMN_POC_SUMMARY),
                        fieldDB.queryFieldByID(results.getInt(COLUMN_POC_FIELD_ID)),
                        pdfDB.queryPDFByID(results.getInt(COLUMN_POC_PDF_ID)),
                        (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID)),
                        (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_ADVISOR_ID)),
                        professor_co_advises_pocDB.queryProfessorsByPocId(results.getInt(COLUMN_POC_ID))));

            }

            return pocs;
        }
    }

    public List<POC> queryPOCsByType(HashSet<POCSearchTypesEnum> searchTypesEnums, String stringToSearchFor) throws SQLException
    {
        if (searchTypesEnums == null || searchTypesEnums.isEmpty())
        {
            throw new SQLException("Select at least one search option.");
        }

        boolean isFirstTime = true;
        StringBuilder fullSQLCommand = new StringBuilder();

        for (POCSearchTypesEnum searchTypesEnum : searchTypesEnums)
        {
            if (!isFirstTime)
            {
                fullSQLCommand.append(" UNION ");
            }

            if (searchTypesEnum == POCSearchTypesEnum.TITLE)
            {
                fullSQLCommand.append(SEARCH_POC_BY_TEXT_TITLE);

            }

            else if (searchTypesEnum == POCSearchTypesEnum.SUMMARY)
            {
                fullSQLCommand.append(SEARCH_POC_BY_TEXT_SUMMARY);
            }

            else if (searchTypesEnum == POCSearchTypesEnum.AUTHOR)
            {
                fullSQLCommand.append(SEARCH_POC_BY_STUDENT);
            }

            else if (searchTypesEnum == POCSearchTypesEnum.ADVISOR)
            {
                fullSQLCommand.append(SEARCH_POC_BY_ADVISOR);
            }

            else if (searchTypesEnum == POCSearchTypesEnum.FIELD)
            {
                fullSQLCommand.append(SEARCH_POC_BY_FIELD);
            }

            isFirstTime = false;
        }
        fullSQLCommand.append(";");

        try (PreparedStatement queryPOCs = conn.prepareStatement(fullSQLCommand.toString()))
        {
            for (int i = 1; i <= searchTypesEnums.size(); i++)
            {
                queryPOCs.setString(i, "%" + stringToSearchFor + "%");
            }

            try (ResultSet results = queryPOCs.executeQuery())
            {
                List<POC> pocs = new ArrayList<>();
                StudentDB studentDB = new StudentDB(conn);
                FieldDB fieldDB = new FieldDB(conn);
                PDFDB pdfDB = new PDFDB(conn);
                UserDB userDB = new UserDB(conn);
                Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
                POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);

                while (results.next())
                {
                    pocs.add(new POC(results.getInt(COLUMN_POC_ID),
                            results.getString(COLUMN_POC_TITLE),
                            studentDB.queryStudentsByPocID(results.getInt(COLUMN_POC_ID)),
                            results.getDate(COLUMN_POC_DEFENSE_DATE).toLocalDate(),
                            poc_has_keywordDB.queryKeywordsByPOCID(results.getInt(COLUMN_POC_ID)),
                            results.getString(COLUMN_POC_SUMMARY),
                            fieldDB.queryFieldByID(results.getInt(COLUMN_POC_FIELD_ID)),
                            pdfDB.queryPDFByID(results.getInt(COLUMN_POC_PDF_ID)),
                            (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID)),
                            (Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_ADVISOR_ID)),
                            professor_co_advises_pocDB.queryProfessorsByPocId(results.getInt(COLUMN_POC_ID))));
                }

                return pocs;
            }
        }
    }

}


