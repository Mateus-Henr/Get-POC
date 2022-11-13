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

    public int getId()
    {
        return id;
    }

    private POC(POCBuilder pocBuilder)
    {
        this.id = pocBuilder.id;
        this.title = pocBuilder.title;
        this.authors = pocBuilder.authors;
        this.defenseDate = pocBuilder.defenseDate;
        this.keywords = pocBuilder.keywords;
        this.summary = pocBuilder.summary;
        this.field = pocBuilder.field;
        this.pdf = pocBuilder.pdf;
        this.registrant = pocBuilder.registrant;
        this.advisor = pocBuilder.advisor;
        this.coAdvisors = pocBuilder.coAdvisors;

    }



    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalDate getDefenseDate()
    {
        return defenseDate;
    }

    public void setDefenseDate(LocalDate defenseDate)
    {
        this.defenseDate = defenseDate;
    }

    public List<Student> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<Student> authors)
    {
        this.authors = authors;
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
        return title;
    }

    public static class POCBuilder
    {
        private int id;
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

        public POCBuilder()
        {
        }

        public POCBuilder id(int id)
        {
            this.id = id;
            return this;
        }

        public POCBuilder title(String title)
        {
            this.title = title;
            return this;
        }

        public POCBuilder defenseDate(LocalDate defenseDate)
        {
            this.defenseDate = defenseDate;
            return this;
        }

        public POCBuilder authors(List<Student> authors)
        {
            this.authors = authors;
            return this;
        }

        public POCBuilder keywords(List<String> keywords)
        {
            this.keywords = keywords;
            return this;
        }

        public POCBuilder summary(String summary)
        {
            this.summary = summary;
            return this;
        }

        public POCBuilder field(Field field)
        {
            this.field = field;
            return this;
        }

        public POCBuilder pdf(PDF pdf)
        {
            this.pdf = pdf;
            return this;
        }

        public POCBuilder registrant(Professor registrant)
        {
            this.registrant = registrant;
            return this;
        }

        public POCBuilder advisor(Professor advisor)
        {
            this.advisor = advisor;
            return this;
        }

        public POCBuilder coAdvisors(List<Professor> coAdvisors)
        {
            this.coAdvisors = coAdvisors;
            return this;
        }

        public POC build()
        {
            return new POC(this);
        }

    }

}