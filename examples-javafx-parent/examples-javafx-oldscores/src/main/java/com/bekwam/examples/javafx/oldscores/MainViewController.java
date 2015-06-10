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
package com.bekwam.examples.javafx.oldscores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for main screen
 *
 * @author carl_000
 */
public class MainViewController extends VBox {

    private Logger logger = LoggerFactory.getLogger(OldScoresApplication.class);

    @FXML
    MenuItem miScores;

    @FXML
    MenuItem miSettings;

    @FXML
    MenuItem miAbout;

    @FXML
    MenuItem miHelp;

    private RecenteredDAO recenteredDAO;
    private SettingsDAO settingsDAO;
    private ScoresDialog scoresDialog;
    private SettingsDialog settingsDialog;
    private AboutDialog aboutDialog;
    private HelpDialog helpDialog;

    @FXML
    public void initialize() throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT]");
        }

        initHelpDialog();  // must be first

        initSettingsDialog();
        initScoresDialog();  // must be after settings dialog
        initAboutDialog();
    }

    private void initAboutDialog() {

        aboutDialog = new AboutDialog();
        aboutDialog.setMainViewRef(this);
    }

    private void initHelpDialog() { helpDialog = new HelpDialog(); }

    private void initSettingsDialog() throws IOException {
        settingsDAO = new SettingsDAOImpl();  // then call init
        settingsDAO.load();

        settingsDialog = new SettingsDialog();
        settingsDialog.setSettingsDAO( settingsDAO );
        settingsDialog.setMainViewRef(this);
    }

    private void initScoresDialog() throws IOException {
        recenteredDAO = new RecenteredDAO();
        recenteredDAO.init();

        scoresDialog = new ScoresDialog();
        scoresDialog.setRecenteredDAO(recenteredDAO);
        scoresDialog.setSettingsDAO(settingsDAO);
        scoresDialog.setMainViewRef(this);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void openMenuItem(ActionEvent evt) {

        try {
            if (evt.getSource() == miScores) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[OPEN SCORES]");
                }

                scoresDialog.show();

            } else if (evt.getSource() == miSettings) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[OPEN SETTINGS]");
                }
                settingsDialog.show();
            } else if (evt.getSource() == miAbout) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[OPEN ABOUT]");
                }
                aboutDialog.show();
            } else if (evt.getSource() == miHelp) {
                openHelpDialog();
            } else {

                String msg = "Unrecognized menu item";

                logger.error(msg);

                Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                alert.showAndWait();
            }

        } catch(Exception exc) {
            String msg = "error showing menu item";
            logger.error( msg, exc );
            Alert alert = new Alert(Alert.AlertType.ERROR, msg);
            alert.showAndWait();
        }
    }

    public void openHelpDialog() throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("[OPEN HELP]");
        }
        helpDialog.show();
    }

}
