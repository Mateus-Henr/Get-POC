package com.ufv.project.model;

public class Student extends User
{
    private String registration;
    private String email;
    private POC poc;

    public Student(String username, String name, String password, String registration, String email)
    {
        super(username, name, password, UserTypesEnum.STUDENT);
        this.registration = registration;
        this.email = email;
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

    public POC getPoc()
    {
        return poc;
    }

    public void setPoc(POC poc)
    {
        this.poc = poc;
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

}
