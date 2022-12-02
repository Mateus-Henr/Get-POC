package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.POCDB;
import com.ufv.project.db.POCSearchTypesEnum;
import com.ufv.project.model.DataModel;
import com.ufv.project.model.POC;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class SearchPOCControllerFX
{
    @FXML
    private TextField searchPOCTextField;

    @FXML
    private ImageView noPOCImage;

    @FXML
    private ListView<POC> pocListView;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private CheckBox searchByTitleCheckBox;

    @FXML
    private CheckBox searchBySummaryCheckBox;

    @FXML
    private CheckBox searchByAuthorCheckBox;

    @FXML
    private CheckBox searchByAdvisorCheckBox;

    @FXML
    private CheckBox searchByFieldCheckBox;

    private final DataModel dataModel;

    /**
     * Constructor for SearchPOCControllerFX.
     */
    public SearchPOCControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    /**
     * Runs upon initialization.
     */
    @FXML
    public void initialize()
    {
        // Set event handling to text box when pressing the "ENTER" key.
        searchPOCTextField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && !searchPOCTextField.getText().trim().isEmpty())
            {
                handlePOCSearching();
            }
        });

        // Take away focus from the box.
        searchPOCTextField.setFocusTraversable(false);

        pocListView.getSelectionModel().selectedItemProperty().addListener((observableValue, poc, t1) ->
                ((AnalyzePOCControllerFX) Main.loadStageWithDataModel("analyze-poc-page-view.fxml", dataModel, "Analyze POC"))
                        .setUpPOCData(pocListView.getSelectionModel().getSelectedItem()));
    }

    /**
     * Searches for POC.
     */
    @FXML
    public void handlePOCSearching()
    {
        final Task<List<POC>> searchPOCTask = new Task<>()
        {
            @Override
            protected List<POC> call() throws SQLException
            {
                try (ConnectDB connectDB = new ConnectDB())
                {
                    return new POCDB(connectDB.getConnection()).queryPOCsByType(getSearchTypes(),
                            searchPOCTextField.getText().trim());
                }
            }
        };

        searchPOCTask.setOnSucceeded(workerStateEvent ->
        {
            List<POC> pocList = searchPOCTask.getValue();

            if (pocList == null || pocList.isEmpty())
            {
                File file = new File("src/main/resources/com/ufv/project/images/anonymous_user.png");

                noPOCImage.setImage(new Image(file.toURI().toString()));
                return;
            }

            // Transfer items to list in the view.
            pocListView.setItems(FXCollections.observableList(pocList));
        });

        searchPOCTask.setOnFailed(workerStateEvent -> new Alert(Alert.AlertType.ERROR,
                "Couldn't get POCs: " + searchPOCTask.getException().getMessage(),
                ButtonType.OK));

        progressIndicator.progressProperty().bind(searchPOCTask.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(searchPOCTask.runningProperty()).then(true).otherwise(false));
        progressIndicator.managedProperty().bind(Bindings.when(searchPOCTask.runningProperty()).then(true).otherwise(false));

        new Thread(searchPOCTask).start();
    }

    /**
     * Gets search types.
     *
     * @return list containing search types.
     */
    private HashSet<POCSearchTypesEnum> getSearchTypes()
    {
        HashSet<POCSearchTypesEnum> searchTypes = new HashSet<>();

        if (searchByTitleCheckBox.isSelected())
        {
            searchTypes.add(POCSearchTypesEnum.TITLE);
        }

        if (searchBySummaryCheckBox.isSelected())
        {
            searchTypes.add(POCSearchTypesEnum.SUMMARY);
        }

        if (searchByAuthorCheckBox.isSelected())
        {
            searchTypes.add(POCSearchTypesEnum.AUTHOR);
        }

        if (searchByAdvisorCheckBox.isSelected())
        {
            searchTypes.add(POCSearchTypesEnum.ADVISOR);
        }

        if (searchByFieldCheckBox.isSelected())
        {
            searchTypes.add(POCSearchTypesEnum.FIELD);
        }

        return searchTypes;
    }

}