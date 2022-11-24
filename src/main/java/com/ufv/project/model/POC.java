package com.ufv.project.model;

import java.time.LocalDate;
import java.util.List;

public class POC
{
    private final int id;
    private String title;
    private List<Student> authors;
    private LocalDate defenseDate;
    private List<String> keywords;
    private String summary;
    private Field field;
    private PDF pdf;
    private Professor registrant;
    private Professor advisor;
    private List<Professor> coAdvisors;

    public POC(int id, String title, List<Student> authors, LocalDate defenseDate, List<String> keywords, String summary,
               Field field, PDF pdf, Professor registrant, Professor advisor, List<Professor> coAdvisors)
    {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.defenseDate = defenseDate;
        this.keywords = keywords;
        this.summary = summary;
        this.field = field;
        this.pdf = pdf;
        this.registrant = registrant;
        this.advisor = advisor;
        this.coAdvisors = coAdvisors;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Student> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<Student> authors)
    {
        this.authors = authors;
    }

    public LocalDate getDefenseDate()
    {
        return defenseDate;
    }

    public void setDefenseDate(LocalDate defenseDate)
    {
        this.defenseDate = defenseDate;
    }

    public List<String> getKeywords()
    {
        return keywords;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    public PDF getPdf()
    {
        return pdf;
    }

    public void setPdf(PDF pdf)
    {
        this.pdf = pdf;
    }

    public Professor getRegistrant()
    {
        return registrant;
    }

    public void setRegistrant(Professor registrant)
    {
        this.registrant = registrant;
    }

    public Professor getAdvisor()
    {
        return advisor;
    }

    public void setAdvisor(Professor advisor)
    {
        this.advisor = advisor;
    }

    public List<Professor> getCoAdvisors()
    {
        return coAdvisors;
    }

    public void setCoAdvisors(List<Professor> coAdvisors)
    {
        this.coAdvisors = coAdvisors;
    }

    @Override
    public String toString()
    {
        return title + " -> " + summary;
    }

}