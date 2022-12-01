package com.ufv.project.db;

import com.ufv.project.model.Student;
import com.ufv.project.model.UserTypesEnum;

import java.sql.*;
import java.util.List;

public class StudentDB
{
    /* Table Student constants. */
    public static final String TABLE_STUDENT = "TB_Student";
    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_STUDENT_REGISTRATION = "Registration";
    public static final String COLUMN_STUDENT_POC = "POC";
    public static final String COLUMN_USER_STUDENT_ID = "TB_User_ID";

    /* Table Student constants indexes. */

    private static final int COLUMN_STUDENT_EMAIL_INDEX = 1;
    private static final int COLUMN_STUDENT_REGISTRATION_INDEX = 2;
    private static final int COLUMN_STUDENT_POC_INDEX = 3;
    private static final int COLUMN_USER_STUDENT_ID_INDEX = 4;

    /* Table Student queries. */

    private static final String QUERY_STUDENT = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";
    private static final String QUERY_STUDENTS = "SELECT * FROM " + TABLE_STUDENT;

    private static final String QUERY_STUDENT_BY_POC_ID = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_POC + " = ?";
    private static final String INSERT_STUDENT = "INSERT INTO " + TABLE_STUDENT + " (" + COLUMN_STUDENT_EMAIL + ", " + COLUMN_STUDENT_REGISTRATION + ", " + COLUMN_STUDENT_POC + ", " + COLUMN_USER_STUDENT_ID + ") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_REGISTRATION + " = ?, " + COLUMN_STUDENT_EMAIL + " = ?, " + COLUMN_STUDENT_POC + " = ? WHERE " + COLUMN_USER_STUDENT_ID + " = ?";
    private static final String DELETE_STUDENT = "DELETE FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    private static final String SET_STUDENT_POC_NULL = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_POC + " = NULL WHERE " + COLUMN_STUDENT_POC + " = ?";
    private static final String SET_STUDENT_POC = "UPDATE " + TABLE_STUDENT + " SET " + COLUMN_STUDENT_POC + " = ? WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    /* Connection to the database. */
    private final Connection conn;


    public StudentDB(Connection conn)
    {
        this.conn = conn;
    }

    /* Query a Student.
     * @param   username   username of the Student to query.
     * @param   password   password of the Student to query.
     * @param   name       name of the Student to query.
     * @return  Student with the given username, password and name.
     */
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

    /* Query Students by POC ID.
     * @param   POCID   id of the poc that the students are associated with.
     * @return  List of Students with the given POC ID.
     */

    public List<Student> queryStudentsByPocID(int POCID) throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Student) user)
                .filter(student -> student.getPOCID() == POCID)
                .toList();
    }

    /* Insert a Student.
     * @param   student   Student to insert.
     * @return  username of the inserted Student.
     */

    protected String insertStudent(Student student) throws SQLException
    {
        try (PreparedStatement insertStudent = conn.prepareStatement(INSERT_STUDENT))
        {
            insertStudent.setString(COLUMN_STUDENT_EMAIL_INDEX, student.getEmail());
            insertStudent.setString(COLUMN_STUDENT_REGISTRATION_INDEX, student.getRegistration());

            if (student.getPOCID() == 0)
            {
                insertStudent.setNull(COLUMN_STUDENT_POC_INDEX, Types.INTEGER);
            }
            else
            {
                insertStudent.setInt(COLUMN_STUDENT_POC_INDEX, student.getPOCID());
            }

            insertStudent.setString(COLUMN_USER_STUDENT_ID_INDEX, student.getUsername());

            if (insertStudent.executeUpdate() != 1)
            {
                throw new SQLException("ERROR: Couldn't insert student with username: '" + student.getUsername() + "'.");
            }

            return student.getUsername();
        }
    }

    /* Delete a Student.
     * @param   username   username of the Student to delete.
     * @param   password   password of the Student to delete.
     * @param   name       name of the Student to delete.
     * @return  Deleted Student.
     */

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

    /* Update a Student.
     * @param   newStudent   Student to update.
     * @return  Updated Student.
     */

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
                oldStudent.setPOCID(newStudent.getPOCID());
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

            return newStudent;
        }
    }

    /* Set a Student POC null.
     * @param   POCID      id of the POC to set.
     */

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

    /* Set a Student POC.
     * @param   POCID      id of the POC to set.
     * @param   studentID  id of the student to set.
     */

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

    /* Query Students by POC ID.
     * @param   POCID   id of the poc that the students are associated with.
     * @return  List of Students with the given POC ID.
     */

    public List<Student> querStudents() throws SQLException
    {
        return new UserDB(conn).queryUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Student) user)
                .toList();
    }

}
