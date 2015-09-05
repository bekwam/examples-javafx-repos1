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
package com.bekwam.examples.javafx.menubutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Main JavaFX Controller for app
 *
 * @author carl_000
 */
public class SyncCollegeSelectionController {

    private Logger logger = LoggerFactory.getLogger(SyncCollegeSelectionController.class);

    @FXML
    MenuButton mbPrefCollege;

    @FXML
    TextField tfPrefCollege;

    @FXML
    Label lblStatus;

    @FXML
    ProgressBar pbStatus;

    private BekwamDotNetDAO dao = new BekwamDotNetDAO();

    private EventHandler<ActionEvent> successHandler = (evt) -> {
        MenuItem mi = (MenuItem)evt.getSource();
        tfPrefCollege.setText( mi.getText() );
    };

    @FXML
    public void triggerMbPrefCollege() {

        lblStatus.setVisible(true);
        pbStatus.setVisible(true);
        lblStatus.setText("Loading...");
        pbStatus.setProgress(0.1d);

        PreferredColleges preferredColleges = dao.findPreferredColleges();
        List<String> collegeList = preferredColleges
                        .getPreferredColleges()
                        .stream()
                        .map(College::getCollegeName)
                        .collect(Collectors.toList());

        pbStatus.setProgress(0.9d);

        List<MenuItem> menuItems = collegeList
                    .stream()
                    .map((s) -> {
                        MenuItem mi = new MenuItem(s);
                        mi.setOnAction(successHandler);
                        return mi;
                    })
                    .collect(Collectors.toList());


        mbPrefCollege.getItems().clear();
        mbPrefCollege.getItems().addAll(menuItems);
        mbPrefCollege.show();

        lblStatus.setText("");
        pbStatus.setProgress(0.0d);

        lblStatus.setVisible(false);
        pbStatus.setVisible(false);

    }
}
