package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.POC;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SearchPOCController
{
    // ---------- Top Menu ----------
    @FXML
    private TopMenuController topMenuController;
    // ------------------------------

    // --------- Search POC ---------
    @FXML
    private TextField searchPOCTextField;

    @FXML
    private ImageView noPOCImage;

    @FXML
    private ListView<POC> pocListView;
    // ------------------------------

    @FXML
    public void initialize()
    {
        // Take away focus from the box.
        searchPOCTextField.setFocusTraversable(false);

        // Hide list while user has not searched for the poc.
        pocListView.setVisible(false);
    }

    @FXML
    public void handlePOCSearching()
    {
        // Load items from database.
        ObservableList<POC> pocList = Singleton.getInstance().getPocList();

        if (pocList.isEmpty())
        {
            File file = new File("src/main/resources/com/ufv/project/images/anonymous_user.png");

            noPOCImage.setImage(new Image(file.toURI().toString()));
            return;
        }

        // Transfer items to list in the view.
        pocListView.setItems(pocList);

        // Shows up list with the results.
        pocListView.setVisible(true);
    }

}
