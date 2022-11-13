package com.ufv.project.db;

import com.ufv.project.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class POCDB {

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

    private static final String DELETE_POC = "DELETE FROM " + TABLE_POC + " WHERE " + COLUMN_POC_ID + " = ?";

    private static final String UPDATE_POC = "UPDATE " + TABLE_POC + " SET " + COLUMN_POC_TITLE + " = ?, " + COLUMN_POC_DEFENSE_DATE + " = ?, " + COLUMN_POC_SUMMARY + " = ?, " + COLUMN_POC_FIELD_ID + " = ?, " + COLUMN_POC_PDF_ID + " = ?, " + COLUMN_POC_TEACHER_REGISTRANT_ID + " = ?, " + COLUMN_POC_TEACHER_ADVISOR_ID + " = ? WHERE " + COLUMN_POC_ID + " = ?";


    private PreparedStatement queryPOC;
    private PreparedStatement queryPOCs;
    private PreparedStatement insertPOC;
    private PreparedStatement deletePOC;
    private PreparedStatement updatePOC;


    private Connection conn;

    public POCDB(Connection conn) throws SQLException {
        this.conn = conn;
        try {
            queryPOC = conn.prepareStatement(QUERY_POC);
            queryPOCs = conn.prepareStatement(QUERY_POCs);
            insertPOC = conn.prepareStatement(INSERT_POC, Statement.RETURN_GENERATED_KEYS);
            deletePOC = conn.prepareStatement(DELETE_POC);
            updatePOC = conn.prepareStatement(UPDATE_POC);
        } catch (SQLException e) {
            System.out.println("Error preparing statements: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public POC queryPOC(int id) throws SQLException {
        //All keywords
        POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
        List<String> keywords = poc_has_keywordDB.queryKeywordsByPOCID(id);

        //All co-advisors
        Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
        List<Professor> professors = professor_co_advises_pocDB.queryProfessorsByPocId(id);

        //All authors
        StudentDB studentDB = new StudentDB(conn);
        List<Student> students = studentDB.queryStudentsByPocID(id);

        FieldDB fieldDB = new FieldDB(conn);
        PDFDB pdfDB = new PDFDB(conn);
        ProfessorDB professorDB = new ProfessorDB(conn);
        UserDB userDB = new UserDB(conn);


        try {
            queryPOC.setInt(1, id);
            ResultSet results = queryPOC.executeQuery();
            if (results.next()) {
                return new POC.POCBuilder()
                        .id(results.getInt(COLUMN_POC_ID_INDEX))
                        .title(results.getString(COLUMN_POC_TITLE_INDEX))
                        .authors(students)
                        .defenseDate(results.getDate(COLUMN_POC_DEFENSE_DATE_INDEX).toLocalDate())
                        .keywords(keywords)
                        .summary(results.getString(COLUMN_POC_SUMMARY_INDEX))
                        .field(fieldDB.queryFieldByID(results.getInt(COLUMN_POC_FIELD_INDEX)))
                        .pdf(pdfDB.queryPDFByID(results.getInt(COLUMN_POC_PDF_ID_INDEX)))
                        .registrant((Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID_INDEX)))
                        .advisor((Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_ADVISOR_ID_INDEX)))
                        .coAdvisors(professors)
                        .build();
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public int insertPOC(POC poc) {
        try {

            insertPOC.setInt(1, poc.getId());
            insertPOC.setString(2, poc.getTitle());
            insertPOC.setString(3, Date.valueOf(poc.getDefenseDate()).toString());
            insertPOC.setString(4, poc.getSummary());
            insertPOC.setInt(5, poc.getField().getId());
            insertPOC.setInt(6, poc.getPdf().getId());
            insertPOC.setString(7, poc.getRegistrant().getUsername());
            insertPOC.setString(8, poc.getAdvisor().getUsername());


            int affectedRows = insertPOC.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert POC!");
            }
            return affectedRows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public POC deletePOC(int id) throws SQLException {
        POC poc = queryPOC(id);
        POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
        poc_has_keywordDB.deletePOC_has_Keyword(id);

        Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
        professor_co_advises_pocDB.deleteProfessor_co_advises_poc(id);

        StudentDB studentDB = new StudentDB(conn);
        studentDB.setStudentPOCNull(id);

        try {
            deletePOC.setInt(1, id);
            int affectedRows = deletePOC.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't delete POC!");
            }
            return poc;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return null;
        }

    }

    public POC updatePOC(POC poc) throws SQLException {
        POC old = queryPOC(poc.getId());

        //Update Keywords
        POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
        poc_has_keywordDB.deletePOC_has_Keyword(poc.getId());
        for (String keyword : poc.getKeywords()) {
            poc_has_keywordDB.insertPOC_has_Keyword(poc.getId(), keyword);
        }

        //Update Co-Advisors
        Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);
        professor_co_advises_pocDB.deleteProfessor_co_advises_poc(poc.getId());
        for (Professor professor : poc.getCoAdvisors()) {
            professor_co_advises_pocDB.insertProfessor_co_advises_poc(professor.getUsername(), poc.getId());
        }

        //Update Authors
        StudentDB studentDB = new StudentDB(conn);
        studentDB.setStudentPOCNull(poc.getId());
        for (Student student : poc.getAuthors()) {
            studentDB.setStudentPOC(student.getUsername(), poc.getId());
        }


        try {
            if (old != null) {
                if (poc.getTitle() != null) {
                    updatePOC.setString(1, poc.getTitle());
                } else {
                    updatePOC.setString(1, old.getTitle());
                }

                if (poc.getDefenseDate() != null) {
                    updatePOC.setString(2, Date.valueOf(poc.getDefenseDate()).toString());
                } else {
                    updatePOC.setString(2, Date.valueOf(old.getDefenseDate()).toString());
                }

                if (poc.getSummary() != null) {
                    updatePOC.setString(3, poc.getSummary());
                } else {
                    updatePOC.setString(3, old.getSummary());
                }

                if (poc.getField() != null) {
                    updatePOC.setInt(4, poc.getField().getId());
                } else {
                    updatePOC.setInt(4, old.getField().getId());
                }

                if (poc.getPdf() != null) {
                    updatePOC.setInt(5, poc.getPdf().getId());
                } else {
                    updatePOC.setInt(5, old.getPdf().getId());
                }

                if (poc.getRegistrant() != null) {
                    updatePOC.setString(6, poc.getRegistrant().getUsername());
                } else {
                    updatePOC.setString(6, old.getRegistrant().getUsername());
                }

                if (poc.getAdvisor() != null) {
                    updatePOC.setString(7, poc.getAdvisor().getUsername());
                } else {
                    updatePOC.setString(7, old.getAdvisor().getUsername());
                }

                updatePOC.setInt(8, poc.getId());

                int affectedRows = updatePOC.executeUpdate();
                if (affectedRows != 1) {
                    throw new SQLException("Couldn't update POC!");
                }
                return poc;


            }
            return null;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return null;
        }
    }

    //lista com todos pocs

    public List<POC> queryAllPOCs() throws SQLException {

        List<POC> pocs = new ArrayList<>();
        ResultSet results = queryPOCs.executeQuery();

        StudentDB studentDB = new StudentDB(conn);
        FieldDB fieldDB = new FieldDB(conn);
        PDFDB pdfDB = new PDFDB(conn);
        UserDB userDB = new UserDB(conn);
        Professor_co_advises_pocDB professor_co_advises_pocDB = new Professor_co_advises_pocDB(conn);

        POC_has_KeywordDB poc_has_keywordDB = new POC_has_KeywordDB(conn);
        while (results.next()) {
            pocs.add(new POC.POCBuilder()
                    .id(results.getInt(COLUMN_POC_ID))
                    .title(results.getString(COLUMN_POC_TITLE))
                    .authors(studentDB.queryStudentsByPocID(results.getInt(COLUMN_POC_ID)))
                    .defenseDate(results.getDate(COLUMN_POC_DEFENSE_DATE).toLocalDate())
                    .keywords(poc_has_keywordDB.queryKeywordsByPOCID(results.getInt(COLUMN_POC_ID)))
                    .summary(results.getString(COLUMN_POC_SUMMARY))
                    .field(fieldDB.queryFieldByID(results.getInt(COLUMN_POC_FIELD_ID)))
                    .pdf(pdfDB.queryPDFByID(results.getInt(COLUMN_POC_PDF_ID)))
                    .registrant((Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_REGISTRANT_ID)))
                    .advisor((Professor) userDB.queryUserByID(results.getString(COLUMN_POC_TEACHER_ADVISOR_ID)))
                    .coAdvisors(professor_co_advises_pocDB.queryProfessorsByPocId(results.getInt(COLUMN_POC_ID)))
                    .build());

        }
        return pocs;
    }

}
