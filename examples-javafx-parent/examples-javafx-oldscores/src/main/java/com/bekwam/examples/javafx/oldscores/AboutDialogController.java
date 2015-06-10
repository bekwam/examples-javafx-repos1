package com.bekwam.examples.javafx.oldscores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 5/31/2015.
 */
public class AboutDialogController extends VBox {

    private Logger logger = LoggerFactory.getLogger(AboutDialogController.class);

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
