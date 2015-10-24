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
package com.bekwam.examples.javafx.userdata;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * @author carl_000
 */
public class ListController {

    private final static String PROP_COMMAND_MESSAGE = "commandMessage";

    @FXML
    ListView<Node> lvButtons;

    private EventHandler<ActionEvent> commandHandler = (evt) -> {

        Node n = (Node)evt.getSource();
        String id = (String) n.getUserData();
        String commandMessage = (String)n.getProperties().get(PROP_COMMAND_MESSAGE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, commandMessage + " " + id);
        alert.showAndWait();
    };

    @FXML
    public void initialize() {

        String[][] buttonDefs = {
                {"Edit", "Editting"},
                {"Run", "Running"},
                {"Send", "Sending"}
        };

        String[][] data = {
                {"A", "ID: A001"},
                {"B", "ID: B001"},
                {"C", "ID: C001"},
                {"D", "ID: D001"},
                {"E", "ID: E001"}
        };

        //
        // Renders
        //
        // A | Edit A button | Run A button | Send A button
        // B | Edit B button | Run B button | Send B button
        // ...
        //

        for( String[] rec : data ) {

            HBox hbox = new HBox();
            hbox.setSpacing(20.0d);

            Label label= new Label( rec[0] );
            hbox.getChildren().add(label);

            for( String[] buttonDef : buttonDefs ) {
                Button btn = new Button(buttonDef[0]);
                btn.getProperties().put(PROP_COMMAND_MESSAGE, buttonDef[1]);
                btn.setUserData(rec[1]);
                btn.setOnAction(commandHandler);
                hbox.getChildren().add(btn);
            }

            lvButtons.getItems().add( hbox );
        }
    }
}
