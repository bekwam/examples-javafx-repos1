/*
 * Copyright 2015 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekwam.examples.javafx.oldscores1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for Settings screen
 *
 * @author carl_000
 */
public class SettingsDialogController extends VBox {

    private final Logger logger = LoggerFactory.getLogger(SettingsDialogController.class);

    private SettingsDAO settingsDAO;

    @FXML
    RadioButton rbRoundUp;

    @FXML
    public void save(ActionEvent evt)  {

        boolean roundUp = rbRoundUp.isSelected();

        settingsDAO.setRoundUp( roundUp );

        try {
            settingsDAO.save();
        } catch(IOException exc) {

            String msg = "Error saving settings to '" + settingsDAO.getAbsolutePath() + "'";

            logger.error( msg, exc );

           Alert alert = new Alert(Alert.AlertType.ERROR, msg);
            alert.showAndWait();

        }

        close(evt);
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

    public void setSettingsDAO(SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
    }
}
