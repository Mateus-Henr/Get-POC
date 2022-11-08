package com.ufv.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class POC
{
    private final int id;
    private String title;
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
        this.defenseDate = pocBuilder.defenseDate;
        this.summary = pocBuilder.summary;
        this.field = pocBuilder.field;
        this.pdf = pocBuilder.pdf;
        this.registrant = pocBuilder.registrant;
        this.advisor = pocBuilder.advisor;
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

    @Override
    public String toString()
    {
        return title;
    }

    public static class POCBuilder
    {
        private int id;
        private String title;
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
            keywords = new ArrayList<>();
            coAdvisors = new ArrayList<>();
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