package com.ufv.project.model;

import java.time.LocalDate;
import java.util.List;

// Worth looking into Builder design pattern implementation for this class.
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

    public POC(int id, String title, LocalDate defenseDate, String summary, Field field, PDF pdf, Professor registrant, Professor advisor)
    {
        this.id = id;
        this.title = title;
        this.defenseDate = defenseDate;
        this.summary = summary;
        this.field = field;
        this.pdf = pdf;
        this.registrant = registrant;
        this.advisor = advisor;
    }

}
