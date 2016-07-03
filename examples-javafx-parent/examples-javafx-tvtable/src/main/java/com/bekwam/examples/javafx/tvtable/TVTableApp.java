package com.bekwam.examples.javafx.tvtable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for TVTable demo
 *
 * @author carl
 */
public class TVTableApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader( TVTableApp.class.getResource("/tvtable-fxml/TVTable.fxml"));

        Parent p = fxmlLoader.load();

        TVTableController c = fxmlLoader.getController();

        Scene scene = new Scene(p, 1024, 768);
        scene.getStylesheets().add( "css/tvtable.css" );

        primaryStage.setScene( scene );
        primaryStage.setOnShown( (evt) -> c.load() );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
