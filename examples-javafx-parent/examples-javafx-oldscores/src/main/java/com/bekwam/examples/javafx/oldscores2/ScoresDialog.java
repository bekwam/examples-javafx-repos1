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

import com.bekwam.examples.javafx.oldscores2.data.RecenteredDAO;
import com.bekwam.examples.javafx.oldscores2.data.SettingsDAO;
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
 * Dialog for Scores screen
 *
 * @author carl_000
 */
public class ScoresDialog {

    private final Logger logger = LoggerFactory.getLogger(ScoresDialog.class);

    private Stage stage;
    private RecenteredDAO recenteredDAO;
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

            ScoresDialogController vbox = new ScoresDialogController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml2/ScoresDialog.fxml"));
            loader.setRoot(vbox);

            loader.load();

            ((ScoresDialogController)loader.getController()).setDao( recenteredDAO );  // controller != root
            ((ScoresDialogController)loader.getController()).setSettingsDAO( settingsDAO );
            ((ScoresDialogController)loader.getController()).setMainViewRef( mainViewRef.get() );

            if(logger.isDebugEnabled() ) {
                logger.debug("[SHOW] root=" + loader.getRoot().hashCode());
                logger.debug("[SHOW] controller=" + loader.getController().hashCode());
            }

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
            stage.setTitle("Pre-1995 and Recentered Scores");
            stage.setScene(scene);
        }

        if( !stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not showing");
            }
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

    public void setRecenteredDAO(RecenteredDAO recenteredDAO) {
       this.recenteredDAO = recenteredDAO;
    }

    public void setSettingsDAO(SettingsDAO settingsDAO) { this.settingsDAO = settingsDAO; }

    public void setMainViewRef(MainViewController mainView) {
        mainViewRef = new WeakReference<>(mainView);
    }

}
