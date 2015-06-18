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
package com.bekwam.examples.javafx.oldscores3;

import com.bekwam.examples.javafx.oldscores3.data.RecenteredDAO;
import com.bekwam.examples.javafx.oldscores3.data.SettingsDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;

/**
 * Controller for main screen
 *
 * @author carl_000
 */
public class MainViewController extends VBox {

    private final Logger logger = LoggerFactory.getLogger(OldScoresApplication3.class);

    @FXML
    MenuItem miScores;

    @FXML
    MenuItem miSettings;

    @FXML
    MenuItem miAbout;

    @FXML
    MenuItem miHelp;

    @Inject
    SettingsDAO settingsDAO;

    @Inject
    RecenteredDAO recenteredDAO;

    @Inject
    Provider<ScoresView> scoresViewProvider;

    @Inject
    Provider<SettingsView> settingsViewProvider;

    @Inject
    Provider<AboutView> aboutViewProvider;

    @Inject
    Provider<HelpView> helpViewProvider;

    @Inject
    NavigationDelegate navigationDelegate;

    private ScoresView scoresView;
    private SettingsView settingsView;
    private AboutView aboutView;
    private HelpView helpView;

    @FXML
    public void initialize() throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT]");
        }

        initHelpDialog();
        initSettingsDialog();
        initScoresDialog();
        initAboutDialog();
    }

    private void initAboutDialog() {

        aboutView = aboutViewProvider.get();
    }

    private void initHelpDialog() { helpView = helpViewProvider.get(); }

    private void initSettingsDialog() throws IOException {

        settingsView = settingsViewProvider.get();
    }

    private void initScoresDialog() throws IOException {

        scoresView = scoresViewProvider.get();
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

                scoresView.show();

            } else if (evt.getSource() == miSettings) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[OPEN SETTINGS]");
                }
                settingsView.show();
            } else if (evt.getSource() == miAbout) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[OPEN ABOUT]");
                }
                aboutView.show();
            } else if (evt.getSource() == miHelp) {
                navigationDelegate.openHelpDialog();
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

}
