package com.ufv.project.db;

import com.ufv.project.model.Student;
import com.ufv.project.model.Subject;
import com.ufv.project.model.User;
import com.ufv.project.model.UserTypesEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDB
{
    private static final String TABLE_STUDENT = "TB_Student";

    private static final String COLUMN_STUDENT_REGISTRATION = "Registration";
    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_USER_STUDENT_ID = "TB_User_ID";

    private static final int COLUMN_STUDENT_REGISTRATION_INDEX = 1;
    private static final int COLUMN_STUDENT_EMAIL_INDEX = 2;
    private static final int COLUMN_USER_STUDENT_ID_INDEX = 3;

    private static final String GET_STUDENT = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    private static final String INSERT_STUDENT = "INSERT INTO " +
            TABLE_STUDENT + " (" + COLUMN_STUDENT_REGISTRATION + ", " + COLUMN_STUDENT_EMAIL + ", " +
            COLUMN_USER_STUDENT_ID + ") VALUES (?, ?,  ?)";

    private static final String DELETE_STUDENT = "DELETE FROM " + TABLE_STUDENT + " WHERE " + COLUMN_USER_STUDENT_ID + " = ?";

    private PreparedStatement getStudent;
    private PreparedStatement insertStudent;
    private PreparedStatement updateStudent;
    private PreparedStatement deleteStudent;

    private Connection conn;

    public StudentDB(Connection conn)
    {
        this.conn = conn;

        try
        {
            getStudent = conn.prepareStatement(GET_STUDENT);
            insertStudent = conn.prepareStatement(INSERT_STUDENT);
            deleteStudent = conn.prepareStatement(DELETE_STUDENT);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected User getStudentByID(String username, String name, String password) throws SQLException
    {
        getStudent.setString(1, username);

        try (ResultSet resultSet = getStudent.executeQuery())
        {
            if (resultSet.next())
            {
                return new Student(username,
                        name,
                        password,
                        resultSet.getString(COLUMN_STUDENT_REGISTRATION_INDEX),
                        resultSet.getString(COLUMN_STUDENT_EMAIL_INDEX));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected String insertStudent(Student student) throws SQLException
    {
        insertStudent.setString(COLUMN_STUDENT_REGISTRATION_INDEX, student.getRegistration());
        insertStudent.setString(COLUMN_STUDENT_EMAIL_INDEX, student.getEmail());
        insertStudent.setString(COLUMN_USER_STUDENT_ID_INDEX, student.getUsername());

        try (ResultSet resultSet = insertStudent.executeQuery())
        {
            if (resultSet.next())
            {
                return student.getUsername();
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

        return null;
    }

    protected Student deleteStudent(String username, String name, String password) throws SQLException
    {
        deleteStudent.setString(1, username);

        try (ResultSet resultSet = deleteStudent.executeQuery())
        {
            if (resultSet.next())
            {
                return new Student(username,
                        name,
                        password,
                        resultSet.getString(COLUMN_STUDENT_REGISTRATION_INDEX),
                        resultSet.getString(COLUMN_STUDENT_EMAIL_INDEX));
            }
            else
            {
                System.out.println("Error when deleting discipline.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents()
    {
        return new UserDB(conn).getAllUsers().stream()
                .filter(user -> user.getUserType() == UserTypesEnum.STUDENT)
                .map(user -> (Student) user)
                .toList();
    }

}
