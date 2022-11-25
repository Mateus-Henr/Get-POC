package com.ufv.project.controller;

import com.ufv.project.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class TopMenuController
{
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView userIcon;

    @FXML
    private Text userType;

    private final DataModel dataModel;

    public TopMenuController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {
        userType.setText(dataModel.getUserType().toString());
    }

}