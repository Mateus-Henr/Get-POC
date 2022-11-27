package com.ufv.project;

import com.ufv.project.controller.fx.ControllerFactoryFX;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.model.DataModel;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        // Set up db.
        new Thread(new Task<>()
        {
            @Override
            protected Void call() throws Exception
            {
                ConnectDB.initializeDB();

                return null;
            }
        }).start();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/ufv/project/view/login-page-view.fxml"));

        try
        {
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.setTitle("Get-POC App");
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch();
    }

    public static Object loadStageWithDataModel(String filename, DataModel dataModel, String stageTitle)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/ufv/project/view/" + filename));

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