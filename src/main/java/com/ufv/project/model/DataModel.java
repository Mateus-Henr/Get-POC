package com.ufv.project.model;

public class DataModel
{
    private final String username;
    private final String name;
    private final String userType;
    private String email;
    private String registration;

    // Student
    public DataModel(Student student)
    {
        this.username = student.getUsername();
        this.name = student.getName();
        this.userType = student.getUserType().toString();
        this.email = student.getEmail();
        this.registration = student.getRegistration();
    }

    // Professor
    public DataModel(Professor professor)
    {
        this.username = professor.getUsername();
        this.name = professor.getName();
        this.userType = professor.getUserType().toString();
        this.email = professor.getEmail();
    }

    // Admin
    public DataModel(Administrator administrator)
    {
        this.username = administrator.getUsername();
        this.name = administrator.getName();
        this.userType = administrator.getUserType().toString();
    }

    public String getUsername()
    {
        return username;
    }

    public String getName()
    {
        return name;
    }

    public String getUserType()
    {
        return userType;
    }

    public String getEmail()
    {
        return email;
    }

    public String getRegistration()
    {
        return registration;
    }

}
