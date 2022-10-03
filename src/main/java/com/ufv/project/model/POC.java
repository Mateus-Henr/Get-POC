package com.ufv.project.model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class POC
{
    private final int id;
    private String title;
    private ArrayList<String> authors;
    private String advisorName;
    private LocalDate defenseDate;
    private ArrayList<String> keywords;
    private String summary;
    private File pdfFile;
    private User registrant;

    public POC(int id, String title, ArrayList<String> authors, String advisorName, LocalDate defenseDate, ArrayList<String> keywords, String summary, File pdfFile, User registrant)
    {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.advisorName = advisorName;
        this.defenseDate = defenseDate;
        this.keywords = keywords;
        this.summary = summary;
        this.pdfFile = pdfFile;
        this.registrant = registrant;
    }

}
