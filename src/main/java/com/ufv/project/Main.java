package com.ufv.project;

import com.ufv.project.controller.fx.ControllerFactoryFX;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.model.DataModel;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    public static final String PATH_TO_VIEW_FOLDER = "/com/ufv/project/view/";


    /**
     * Initial operations when the app starts up.
     *
     * @param stage current stage.
     */
    @Override
    public void start(Stage stage)
    {
        // Set up db.
        final Task<Void> setUpDB = new Task<>()
        {
            @Override
            protected Void call() throws Exception
            {
                ConnectDB.initializeDB();

                return null;
            }
        };

        // If it succeeds then shows login page.
        setUpDB.setOnSucceeded(workerStateEvent ->
                loadStageWithDataModel("login-page-view.fxml",
                        null,
                        "Get-POC App")
        );

        // If it fails shows dialog.
        setUpDB.setOnFailed(workerStateEvent ->
        {
            new Alert(Alert.AlertType.ERROR,
                    "Couldn't connect with database. Please check your credentials and try again.",
                    ButtonType.OK).showAndWait();
        });

        new Thread(setUpDB).start();
    }

    /**
     * Starts up the app.
     *
     * @param args list of arguments.
     */
    public static void main(String[] args)
    {
        launch();
    }

    /**
     * Loads a new stage with a controller factory using a specific data passed in.
     *
     * @param FXMLFilename filename of the FXML file to be loaded up.
     * @param dataModel    data to be passed to the controller.
     * @param stageTitle   title for the stage.
     * @return controller loaded up with the stage.
     */
    public static Object loadStageWithDataModel(String FXMLFilename, DataModel dataModel, String stageTitle)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(PATH_TO_VIEW_FOLDER + FXMLFilename));

        // Controller factory.
        fxmlLoader.setControllerFactory(ControllerFactoryFX.controllerFactoryWithDataModel(dataModel));

        try
        {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle(stageTitle);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return fxmlLoader.getController();
    }


    /**
     * Closes current stage.
     *
     * @param pane parent pane to close stage up.
     */
    public static void closeCurrentStage(Pane pane)
    {
        if (pane == null)
        {
            return;
        }

        Scene scene = pane.getScene();

        if (scene == null)
        {
            return;
        }

        ((Stage) scene.getWindow()).close();
    }

}