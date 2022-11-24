package com.ufv.project;

import com.ufv.project.db.ConnectDB;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        Main.loadStage("login-page-view.fxml", "Get-POC App");

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
    }

    public static void main(String[] args)
    {
        launch();
    }

    public static Object loadStage(String filename, String stageTitle)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/ufv/project/fxml/" + filename));

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