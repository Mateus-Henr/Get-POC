package com.ufv.project.model;

public class Student extends User
{
    private String registration;
    private String email;
    private POC poc;

    public Student(String username, String name, String password, String registration, String email, POC poc)
    {
        super(username, name, password);
        this.registration = registration;
        this.email = email;
        this.poc = poc;
    }

    public POC searchPOC(String title, String summary, String author, String advisor, String area)
    {
        return null;
    }

}
