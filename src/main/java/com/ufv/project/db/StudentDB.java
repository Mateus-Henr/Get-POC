package com.ufv.project.db;

import com.ufv.project.model.Student;
import com.ufv.project.model.UserTypesEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDB
{
    private static final String TABLE_STUDENT = "TB_Student";
    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_STUDENT_REGISTRATION = "Registration";
    private static final String COLUMN_STUDENT_POC = "POC";
    private static final String COLUMN_USER_STUDENT_ID = "TB_User_ID";

    private static final int COLUMN_STUDENT_EMAIL_INDEX = 1;
    private static final int COLUMN_STUDENT_REGISTRATION_INDEX = 2;
    private static final int COLUMN_STUDENT_POC_INDEX = 3;
    private static final int COLUMN_USER_STUDENT_ID_INDEX = 4;

    private static final String QUERY_STUDENT = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";
    private static final String QUERY_STUDENTS = "SELECT * FROM " + TABLE_STUDENT;

    private static final String QUERY_STUDENT_BY_POC_ID = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_POC + " = ?";
    private static final String INSERT_STUDENT = "INSERT INTO " + TABLE_STUDENT + " (" + COLUMN_STUDENT_EMAIL + ", " + COLUMN_STUDENT_REGISTRATION + ", " + COLUMN_STUDENT_POC + ", " + COLUMN_USER_STUDENT_ID + ") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_REGISTRATION + " = ?, " + COLUMN_STUDENT_EMAIL + " = ?, " + COLUMN_STUDENT_POC + " = ? WHERE " + COLUMN_USER_STUDENT_ID + " = ?";
    private static final String DELETE_STUDENT = "DELETE FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    private static final String SET_STUDENT_POC_NULL = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_POC + " = NULL WHERE " + COLUMN_STUDENT_POC + " = ?";
    private static final String SET_STUDENT_POC = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_POC + " = ? WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    private final Connection conn;


    public StudentDB(Connection conn)
    {
        this.conn = conn;
    }

    protected Student queryStudent(String username, String name, String password) throws SQLException
    {
        try (PreparedStatement queryStudent = conn.prepareStatement(QUERY_STUDENT))
        {
            queryStudent.setString(1, username);

            try (ResultSet resultSet = queryStudent.executeQuery())
            {
                if (resultSet.next())
                {
                    return new Student(username,
                            name,
                            password,
                            resultSet.getString(COLUMN_STUDENT_REGISTRATION_INDEX),
                            resultSet.getInt(COLUMN_STUDENT_POC_INDEX),
                            resultSet.getString(COLUMN_STUDENT_EMAIL_INDEX)
                    );
                }

                return null;
            }
        }
    }

    protected List<Student> queryStudentsByPocID(int POCID) throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Student) user)
                .filter(student -> student.getPOCID() == POCID)
                .toList();
    }

    protected String insertStudent(Student student) throws SQLException
    {
        try (PreparedStatement insertStudent = conn.prepareStatement(INSERT_STUDENT))
        {
            insertStudent.setString(COLUMN_STUDENT_EMAIL_INDEX, student.getEmail());
            insertStudent.setString(COLUMN_STUDENT_REGISTRATION_INDEX, student.getRegistration());
            insertStudent.setInt(COLUMN_STUDENT_POC_INDEX, student.getPOCID());
            insertStudent.setString(COLUMN_USER_STUDENT_ID_INDEX, student.getUsername());

            if (insertStudent.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert student with username: '" + student.getUsername() + "'.");
            }

            return student.getUsername();
        }
    }


    protected Student deleteStudent(String username, String name, String password) throws SQLException
    {
        Student foundStudent = queryStudent(username, name, password);

        if (foundStudent == null)
        {
            throw new SQLException("ERROR: Student with username: '" + username + "' doesn't exist.");
        }

        try (PreparedStatement deleteStudent = conn.prepareStatement(DELETE_STUDENT))
        {
            deleteStudent.setString(1, username);

            if (deleteStudent.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't delete student with username: '" + username + "'.");
            }

            return foundStudent;
        }
    }

    protected Student updateStudent(Student newStudent) throws SQLException
    {
        Student oldStudent = queryStudent(newStudent.getUsername(), newStudent.getName(), newStudent.getPassword());

        if (oldStudent == null)
        {
            throw new SQLException("ERROR: Student with username: '" + newStudent.getUsername() + "' doesn't exist.");
        }

        try (PreparedStatement updateStudent = conn.prepareStatement(UPDATE_STUDENT))
        {
            if (newStudent.getEmail() != null)
            {
                oldStudent.setEmail(newStudent.getEmail());
            }

            if (newStudent.getRegistration() != null)
            {
                oldStudent.setRegistration(newStudent.getRegistration());
            }
            if (newStudent.getPOCID() != 0)
            {
                oldStudent.setPoc_id(newStudent.getPOCID());
            }

            if (newStudent.getUsername() != null)
            {
                oldStudent.setUsername(newStudent.getUsername());
            }

            updateStudent.setString(1, oldStudent.getRegistration());
            updateStudent.setString(2, oldStudent.getEmail());
            updateStudent.setInt(3, oldStudent.getPOCID());
            updateStudent.setString(4, oldStudent.getUsername());

            if (updateStudent.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't update student with username: '" + newStudent.getUsername() + "'.");
            }

            return oldStudent;
        }
    }

    protected void setStudentPOCNull(int POCID) throws SQLException
    {
        try (PreparedStatement setStudentPOCNull = conn.prepareStatement(SET_STUDENT_POC_NULL))
        {
            setStudentPOCNull.setInt(1, POCID);

            if (setStudentPOCNull.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't set POC with ID: '" + POCID + "' to null.");
            }
        }
    }

    protected void setStudentPOC(String studentID, int POCID) throws SQLException
    {
        try (PreparedStatement setStudentPOC = conn.prepareStatement(SET_STUDENT_POC))
        {
            setStudentPOC.setInt(1, POCID);
            setStudentPOC.setString(2, studentID);

            if (setStudentPOC.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't set POC with ID: '" + POCID + "' to student with ID: '" + studentID + "'.");
            }
        }
    }

    public List<Student> getAllStudents() throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Student) user)
                .toList();
    }

}
