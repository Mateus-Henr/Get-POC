//package com.ufv.project.controller.java;
//
//import com.ufv.project.controller.java.TopMenuController;
//import com.ufv.project.controller.java.UpdatePOCController;
//import com.ufv.project.model.DataModel;
//import com.ufv.project.model.POC;
//import com.ufv.project.model.Professor;
//import com.ufv.project.model.Student;
//
//
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//
//public class AnalyzePOCController
//{
//
//
//    private URI pdfPath;
//
//    private final DataModel dataModel;
//
//    public AnalyzePOCController(DataModel dataModel)
//    {
//        this.dataModel = dataModel;
//    }
//
//    public void initialize()
//    {
//
//    }
//
//    @FXML
//    public void onDisplayPDF()
//    {
//        try
//        {
//            Desktop.getDesktop().browse(pdfPath);
//        }
//        catch (IOException e)
//        {
//            System.out.println("ERROR: Couldn't open PDF: " + e.getMessage());
//        }
//    }
//
//    public void setData(POC pocToShow)
//    {
//        titleText.setText(pocToShow.getTitle());
//        authorListView.setItems(FXCollections.observableList(pocToShow.getAuthors()));
//        advisorText.setText(pocToShow.getAdvisor().toString());
//        coAdvisorListView.setItems(FXCollections.observableList(pocToShow.getCoAdvisors()));
//        fieldText.setText(pocToShow.getTitle());
//        dateText.setText(pocToShow.getDefenseDate().toString());
//        keywordList.setItems(FXCollections.observableList(pocToShow.getKeywords()));
//
//        File pdfFile = new File(pocToShow.getPdf().getPath());
//
//        pdfFilepathText.setText(pdfFile.getName());
//        pdfPath = pdfFile.toURI();
//    }
//
//}