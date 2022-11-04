package com.ufv.project;

import com.ufv.project.db.UserDataSingleton;
import com.ufv.project.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        setMockupData();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-poc-page-view.fxml"));

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
    }

}