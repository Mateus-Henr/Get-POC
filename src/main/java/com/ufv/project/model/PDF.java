package com.ufv.project.model;


import java.time.LocalDate;

public class PDF
{
    private final int id;
    private String path;
    private LocalDate lastModificationDate;

    public PDF(int id, String path, LocalDate lastModificationDate)
    {
        this.id = id;
        this.path = path;
        this.lastModificationDate = lastModificationDate;
    }

    public int getId()
    {
        return id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String content)
    {
        this.path = content;
    }

    public LocalDate getLastModificationDate()
    {
        return lastModificationDate;
    }

    public void setLastModificationDate()
    {
        this.lastModificationDate = LocalDate.now();
    }

}