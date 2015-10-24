package com.bekwam.examples.javafx.accesscontrol;/**
 * @author carl_000
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AccessControlMain extends Application {

    private Logger log = LoggerFactory.getLogger(AccessControlMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(AccessControlMain.class.getResource("/fxml/MainView.fxml"));

            Parent p = fxmlLoader.load();

            MainViewController mainView = fxmlLoader.getController();

            Scene scene = new Scene(p);

            primaryStage.setTitle("Access Control");
            primaryStage.setScene(scene);
            primaryStage.setOnShown( (evt) ->  mainView.init() );
            primaryStage.show();

        } catch(IOException exc) {
            log.error( "can't load /fxml/MainView.fxml", exc);
        }
    }

}
