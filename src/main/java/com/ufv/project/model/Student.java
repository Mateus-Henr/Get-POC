package com.ufv.project.model;

public class Student extends User
{
    private String registration;
    private String email;
    private int poc_id;

    public Student(String username, String name, String password, String registration, int POCID, String email)
    {
        super(username, name, password, UserTypesEnum.STUDENT);
        this.registration = registration;
        this.email = email;
        this.poc_id = POCID;
    }

    public String getRegistration()
    {
        return registration;
    }

    public void setRegistration(String registration)
    {
        this.registration = registration;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getPOCID()
    {
        return poc_id;
    }

    public void setPOCID(int poc_id)
    {
        this.poc_id = poc_id;
    }

    @Override
    public boolean canModifyUsers()
    {
        return false;
    }

    @Override
    public boolean canModifyPOCs()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return getUsername() + " - " + registration + " - " + getName();
    }

}