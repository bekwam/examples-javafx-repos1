package com.bekwam.examples.javafx.derived;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by carl on 5/2/16.
 */
public class DerivedApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent p = FXMLLoader.load( DerivedApp.class.getResource("/Derived.fxml"));

        Scene scene = new Scene(p);

        primaryStage.setTitle("Connection Info");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
