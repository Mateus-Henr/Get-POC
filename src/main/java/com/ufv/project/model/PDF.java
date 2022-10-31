package com.ufv.project.model;

import java.io.File;
import java.time.LocalDate;

public class PDF
{
    private final int id;
    private File content;
    private LocalDate lastModificationDate;

    public PDF(int id, File content, LocalDate lastModificationDate)
    {
        this.id = id;
        this.content = content;
        this.lastModificationDate = lastModificationDate;
    }

    public int getId()
    {
        return id;
    }

    public File getContent()
    {
        return content;
    }

    public void setContent(File content)
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
