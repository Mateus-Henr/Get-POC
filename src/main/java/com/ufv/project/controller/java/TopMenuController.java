//package com.ufv.project.controller.java;
//
//import com.ufv.project.model.DataModel;
//import com.ufv.project.model.UserTypesEnum;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class TopMenuController
//{
//
//    public static final String IMAGE_STORAGE_FOLDER = "src" + File.separator +
//            "main" + File.separator +
//            "resources" + File.separator +
//            "com" + File.separator +
//            "ufv" + File.separator +
//            "project" + File.separator + "images" + File.separator;
//
//    private final DataModel dataModel;
//
//
//    public TopMenuController(DataModel dataModel)
//    {
//        this.dataModel = dataModel;
//    }
//
//    @FXML
//    public void initialize()
//    {
//        UserTypesEnum userType = dataModel.getUserType();
//
//        userTypeText.setText(userType.toString());
//
//        try
//        {
//            if (userType == UserTypesEnum.STUDENT)
//            {
//                userIcon.setImage(new Image(new FileInputStream(IMAGE_STORAGE_FOLDER + "student-icon.png")));
//            }
//            else if (userType == UserTypesEnum.PROFESSOR)
//            {
//                userIcon.setImage(new Image(new FileInputStream(IMAGE_STORAGE_FOLDER + "professor-icon.png")));
//            }
//            else if (userType == UserTypesEnum.ADMIN)
//            {
//                userIcon.setImage(new Image(new FileInputStream(IMAGE_STORAGE_FOLDER + "admin-icon.png")));
//            }
//            else
//            {
//                userIcon.setImage(new Image(new FileInputStream(IMAGE_STORAGE_FOLDER + "unknown-icon.png")));
//            }
//        }
//        catch (IOException e)
//        {
//            System.err.println("ERROR: Couldn't load icon for user: " + e.getMessage());
//        }
//    }
//
//    public void onSearchPOCButtonAction(ActionEvent actionEvent) {
//    }
//
//    public void onCreatePOCButtonAction(ActionEvent actionEvent) {
//    }
//
//    public void onLogoutButtonAction(ActionEvent actionEvent) {
//    }
//}