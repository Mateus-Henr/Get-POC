package com.ufv.project.model;

import java.time.LocalDate;

public class PDF
{
    private final int id;
    private String content;
    private LocalDate lastModificationDate;

    public PDF(int id, String content, LocalDate lastModificationDate)
    {
        this.id = id;
        this.content = content;
        this.lastModificationDate = lastModificationDate;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public LocalDate getLastModificationDate()
    {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate)
    {
        this.lastModificationDate = lastModificationDate;
    }

}
