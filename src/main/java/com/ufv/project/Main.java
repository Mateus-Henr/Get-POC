package com.ufv.project;

import com.ufv.project.db.UserDataSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Function;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        setMockupData();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-page-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Get-POC App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    public void setMockupData()
    {
        UserDataSingleton.getInstance().setUsername("Matt");
        UserDataSingleton.getInstance().setEmail("m@gmail.com");
        UserDataSingleton.getInstance().setName("Mateus");
//        UserDataSingleton.getInstance().setUserIcon();
    }

    public static Object loadStage(String filename)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));

        try
        {
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();

            stage.setScene(scene);
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
        Scene scene = pane.getScene();

        if (scene == null)
        {
            return;
        }

        ((Stage) scene.getWindow()).close();
    }

}