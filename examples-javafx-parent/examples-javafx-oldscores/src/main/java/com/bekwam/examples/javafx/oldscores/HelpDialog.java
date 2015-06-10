package com.bekwam.examples.javafx.oldscores;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by carl_000 on 5/31/2015.
 */
public class HelpDialog {

    private Logger logger = LoggerFactory.getLogger(HelpDialog.class);

    private Stage stage;

    public void show() throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[SHOW]");
        }

        if( stage == null ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] creating stage");
            }
            stage = new Stage();

            Parent p = FXMLLoader.load(getClass().getResource("/Help.fxml"));

            Scene scene = new Scene(p);
            scene.getStylesheets().add("/styles.css");
            stage.setTitle("Help");
            stage.setScene(scene);
        }

        if( !stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not showing");
            }
            stage.show();
        }
    }

    public void hide() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[SHOW]");
        }

        if( stage != null && stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not null and is showing");
            }
            stage.hide();
        }
    }
}
