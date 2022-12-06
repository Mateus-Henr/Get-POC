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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class SearchPOCControllerFX
{
    @FXML
    private VBox mainPane;

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
            if (keyEvent.getCode().equals(KeyCode.ENTER))
            {
                onSearchPOCButtonClicked();
            }
        });

        // Take away focus from the box.
        searchPOCTextField.setFocusTraversable(false);

        pocListView.getSelectionModel().selectedItemProperty().addListener((observableValue, poc, t1) ->
        {
            AnalyzePOCControllerFX newScene = (AnalyzePOCControllerFX) Main.loadNewSceneWithDataModel(mainPane, "analyze-poc-page-view.fxml", dataModel, "Analyze POC");

            if (newScene != null)
            {
                newScene.setUpPOCData(pocListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Searches for POC.
     */
    @FXML
    public void onSearchPOCButtonClicked()
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

        // Transfer items to list in the view.
        searchPOCTask.setOnSucceeded(workerStateEvent ->
                pocListView.setItems(FXCollections.observableList(searchPOCTask.getValue())));

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