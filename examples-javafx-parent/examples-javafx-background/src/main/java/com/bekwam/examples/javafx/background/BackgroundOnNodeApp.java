package com.bekwam.examples.javafx.background;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author carl
 */
public class BackgroundOnNodeApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent p = FXMLLoader.load( BackgroundOnNodeApp.class.getResource("/background-fxml/BackgroundOnNode.fxml"));

        Scene scene = new Scene(p);
        scene.getStylesheets().add("/background-css/background.css");

        primaryStage.setScene( scene );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
