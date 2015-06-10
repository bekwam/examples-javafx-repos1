package com.bekwam.examples.javafx.oldscores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by carl_000 on 5/30/2015.
 */
public class OldScoresApplication extends Application {

    private Logger logger = LoggerFactory.getLogger(OldScoresApplication.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        if( logger.isDebugEnabled() ) {
            logger.debug("[START]");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Parent p = loader.load();
        MainViewController mv = loader.getController();

        Scene scene = new Scene( p );

        scene.setOnKeyPressed(evt -> {

            if( evt.getCode().equals(KeyCode.F1) ) {
                try {
                    if( logger.isDebugEnabled() ) {
                        logger.debug("[OPEN HELP]");
                    }
                    mv.openHelpDialog();
                } catch (IOException exc) {
                    String msg = "error showing help dialog";
                    logger.error(msg);
                    Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                    alert.showAndWait();
                }
            }
        });

        primaryStage.setTitle("Old Scores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
