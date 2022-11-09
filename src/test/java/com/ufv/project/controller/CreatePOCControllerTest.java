package com.ufv.project.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.ufv.project.db.Singleton;
import com.ufv.project.db.UserDataSingleton;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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