package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.Field;
import com.ufv.project.model.PDF;
import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

class CreatePOCControllerTest {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField title;

    private File pdfFile;

    @Test
    public void handlePOCAddingTest()
    {
        Singleton.getInstance().addPOC(new POC.POCBuilder()
                .title(title.getText())
                .defenseDate(datePicker.getValue())
                .advisor(new Professor("alan", "Alan", "123456", "alan@ufv.br", new ArrayList<>()))
                .coAdvisors(new ArrayList<>())
                .registrant(new Professor("matt", "Matt", "123456", "matt@ufv.br", new ArrayList<>()))
                .pdf(new PDF(1, pdfFile, LocalDate.now()))
                .field(new Field(1, "sa"))
                .summary("testing")
                .keywords(new ArrayList<>())
                .build());
    }

    @Test
    public void handlePOCAddingTestInvalidDefenseDate()
    {
        try {
            Singleton.getInstance().addPOC(new POC.POCBuilder()
                    .title(title.getText())
                    .defenseDate(datePicker.setValue(LocalDate.of(2050, 10, 10)))
                    .advisor(new Professor("alan", "Alan", "123456", "alan@ufv.br", new ArrayList<>()))
                    .coAdvisors(new ArrayList<>())
                    .registrant(new Professor("matt", "Matt", "123456", "matt@ufv.br", new ArrayList<>()))
                    .pdf(new PDF(1, pdfFile, LocalDate.now()))
                    .field(new Field(1, "name"))
                    .summary("testing")
                    .keywords(new ArrayList<>())
                    .build());
        }
        catch (Exception e) {
            System.out.println("Invalid date");
        }
    }

    @Test
    public void handlePOCAddingTestInvalidPDFDate() {
        try {
            Singleton.getInstance().addPOC(new POC.POCBuilder()
                    .title(title.getText())
                    .defenseDate(datePicker.setValue(LocalDate.now()))
                    .advisor(new Professor("alan", "Alan", "123456", "alan@ufv.br", new ArrayList<>()))
                    .coAdvisors(new ArrayList<>())
                    .registrant(new Professor("matt", "Matt", "123456", "matt@ufv.br", new ArrayList<>()))
                    .pdf(new PDF(1, pdfFile, LocalDate.of(2050, 10, 10)))
                    .field(new Field(1, "name"))
                    .summary("testing")
                    .keywords(new ArrayList<>())
                    .build());
        } catch (Exception e) {
            System.out.println("Invalid date");
        }
    }
}