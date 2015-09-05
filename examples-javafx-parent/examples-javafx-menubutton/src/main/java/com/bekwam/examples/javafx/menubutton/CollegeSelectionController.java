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

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main JavaFX Controller for app
 *
 * @author carl_000
 */
public class CollegeSelectionController {

    private Logger logger = LoggerFactory.getLogger(CollegeSelectionController.class);

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
    public void keyTriggerMbPrefCollege(KeyEvent evt) {

        if( evt.getCode() == KeyCode.ALT || evt.getCode() == KeyCode.TAB ) {
            return;
        }

        triggerMbPrefCollege();
    }

    @FXML
    public void triggerMbPrefCollege() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[TRIGGER]");
        }

        Task<Void> task = new Task<Void>() {

            private Optional<List<String>> collegeList = Optional.empty();

            @Override
            protected Void call() throws Exception {

                updateMessage("Loading colleges...");
                updateProgress(0.1d, 1.0d);
                Platform.runLater( () -> {
                            mbPrefCollege.getItems().clear();
                            mbPrefCollege.getItems().add(new MenuItem("Loading..."));
                        });

                PreferredColleges preferredColleges = dao.findPreferredColleges();
                collegeList = Optional.of(
                        preferredColleges
                                .getPreferredColleges()
                                .stream()
                                .map(College::getCollegeName)
                                .collect(Collectors.toList())
                );
                updateProgress(0.9d, 1.0d);

                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    mbPrefCollege.getItems().clear();
                    if (collegeList.isPresent()) {

                        List<MenuItem> menuItems = collegeList.get()
                                .stream()
                                .map((s) -> {
                                    MenuItem mi = new MenuItem(s);
                                    mi.setOnAction(successHandler);
                                    return mi;
                                })
                                .collect(Collectors.toList());

                        mbPrefCollege.getItems().addAll(menuItems);
                    }
                });
                updateMessage("");
                updateProgress(1.0d, 1.0d);
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                logger.error("find operation cancelled");
                updateMessage("");
                updateProgress(1.0d, 1.0d);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Find operation cancelled");
                    alert.showAndWait();

                    mbPrefCollege.getItems().clear();
                    mbPrefCollege.getItems().add(new MenuItem("Loading..."));
                });
            }

            @Override
            protected void failed() {
                super.failed();
                logger.error("find operation failed", getException());
                updateMessage("");
                updateProgress(1.0d, 1.0d);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Find operation failed with '" + getException().getMessage() + "'");
                    alert.showAndWait();
                });

                mbPrefCollege.getItems().clear();
                mbPrefCollege.getItems().add(new MenuItem("Loading..."));
            }

        };

        lblStatus.textProperty().bind( task.messageProperty() );
        pbStatus.progressProperty().bind( task.progressProperty() );
        lblStatus.visibleProperty().bind( task.runningProperty() );
        pbStatus.visibleProperty().bind( task.runningProperty() );

        new Thread(task).start();

        mbPrefCollege.show();
    }
}
