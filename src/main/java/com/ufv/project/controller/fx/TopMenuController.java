package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.model.DataModel;
import com.ufv.project.model.UserTypesEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TopMenuController
{
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView userIcon;

    @FXML
    private Text userTypeText;

    @FXML
    private MenuItem createPOCMenuItem;

    @FXML
    private MenuItem searchUserMenuItem;

    @FXML
    private MenuItem createUserMenuItem;

    public static final String IMAGE_STORAGE_FOLDER = "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "com" + File.separator +
            "ufv" + File.separator +
            "project" + File.separator + "images" + File.separator;

    private final DataModel dataModel;


    public TopMenuController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {
        UserTypesEnum userType = dataModel.getUserType();

        userTypeText.setText(userType.toString());

        try
        {
            String imageName;

            if (userType == UserTypesEnum.STUDENT)
            {
                imageName = "student-icon.png";

                createPOCMenuItem.setVisible(false);
                searchUserMenuItem.setVisible(false);
                createUserMenuItem.setVisible(false);
            }
            else if (userType == UserTypesEnum.PROFESSOR)
            {
                imageName = "professor-icon.png";

                searchUserMenuItem.setVisible(false);
                createUserMenuItem.setVisible(false);
            }
            else if (userType == UserTypesEnum.ADMIN)
            {
                imageName = "admin-icon.png";
            }
            else
            {
                imageName = "unknown-icon.png";
            }

            userIcon.setImage(new Image(new FileInputStream(IMAGE_STORAGE_FOLDER + imageName)));
        }
        catch (IOException e)
        {
            System.err.println("ERROR: Couldn't load icon for user: " + e.getMessage());
        }
    }

    public void onSearchPOCButtonAction()
    {
        Main.loadStageWithDataModel("search-poc-page-view.fxml", dataModel, "Search POC");
        Main.closeCurrentStage(mainPane);
    }

    public void onCreatePOCButtonAction()
    {
        Main.loadStageWithDataModel("create-poc-page-view.fxml", dataModel, "Search POC");
        Main.closeCurrentStage(mainPane);
    }

    public void onLogoutButtonAction()
    {
        Main.loadStageWithDataModel("login-page-view.fxml", dataModel, "Get-POC App");
        Main.closeCurrentStage(mainPane);
    }

    public void onSearchUserButtonAction()
    {
        Main.loadStageWithDataModel("search-user-page-view.fxml", dataModel, "Get-POC App");
        Main.closeCurrentStage(mainPane);
    }

    public void onCreateUserButtonAction()
    {
        Main.loadStageWithDataModel("create-user-page-view.fxml", dataModel, "Get-POC App");
        Main.closeCurrentStage(mainPane);
    }

}