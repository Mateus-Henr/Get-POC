package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.POC;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SearchPOCController
{
    @FXML
    private ListView<POC> pocListView;

    @FXML
    public void initialize()
    {
        pocListView.setItems(Singleton.getInstance().getPocList());
    }


}
