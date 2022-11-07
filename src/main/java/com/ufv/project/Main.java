package com.ufv.project;

import com.ufv.project.db.UserDataSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        setMockupData();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-poc-page-view.fxml"));

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

}