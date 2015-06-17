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

import com.bekwam.examples.javafx.oldscores2.data.SettingsDAO;
import com.bekwam.examples.javafx.oldscores2.data.SettingsDAOImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Dialog for Settings screen
 *
 * @author carl_000
 */
public class SettingsDialog {

    private final Logger logger = LoggerFactory.getLogger(ScoresDialog.class);

    private Stage stage;
    private SettingsDAO settingsDAO;
    private WeakReference<MainViewController> mainViewRef;

    public void show() throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[SHOW]");
        }

        if( stage == null ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] creating stage");
            }
            stage = new Stage();

            SettingsDialogController vbox = new SettingsDialogController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml2/SettingsDialog.fxml"));
            loader.setRoot(vbox);

            loader.load();

            ((SettingsDialogController)loader.getController()).setSettingsDAO(settingsDAO);  // controller != root

            Scene scene = new Scene(vbox);

            scene.setOnKeyPressed(evt -> {

                if( evt.getCode().equals(KeyCode.F1) ) {
                    try {
                        if( logger.isDebugEnabled() ) {
                            logger.debug("[OPEN HELP]");
                        }
                        if( mainViewRef != null ) {
                            if( mainViewRef.get() != null ) {
                                mainViewRef.get().openHelpDialog();
                            }
                        }
                    } catch (IOException exc) {
                        String msg = "error showing help dialog";
                        logger.error(msg);
                        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                        alert.showAndWait();
                    }
                }
            });

            scene.getStylesheets().add("/styles.css");
            stage.setTitle("Settings");
            stage.setScene(scene);
        }

        if( !stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not showing");
            }

            ((SettingsDAOImpl)settingsDAO).markForRefresh();

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

    public void setSettingsDAO(SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
    }

    public void setMainViewRef(MainViewController mainView) {
        mainViewRef = new WeakReference<>(mainView);
    }

}
