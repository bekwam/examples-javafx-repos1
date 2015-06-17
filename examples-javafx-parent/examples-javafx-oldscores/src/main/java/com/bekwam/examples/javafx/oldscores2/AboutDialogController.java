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
package com.bekwam.examples.javafx.oldscores2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for About screen
 *
 * @author carl_000
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
