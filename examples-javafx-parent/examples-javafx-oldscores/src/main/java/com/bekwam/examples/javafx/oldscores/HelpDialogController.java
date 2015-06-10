package com.bekwam.examples.javafx.oldscores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 5/31/2015.
 */
public class HelpDialogController extends VBox {

    private Logger logger = LoggerFactory.getLogger(HelpDialogController.class);

    @FXML
    WebView wv;

    @FXML
    public void initialize() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT]");
        }

        String url = getClass().getResource("/help/help.html").toString();

        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT] getting help from url={}", url);
        }

        wv.getEngine().load( url );
    }

    @FXML
    public void close(ActionEvent evt) {

        //
        // For some reason, this.getScene() which is on the fx:root returns null
        //

        Scene scene = ((Button)evt.getSource()).getScene();
        if( scene != null ) {
            Window w = scene.getWindow();
            if (w != null) {
                w.hide();
            }
        }
    }
}
