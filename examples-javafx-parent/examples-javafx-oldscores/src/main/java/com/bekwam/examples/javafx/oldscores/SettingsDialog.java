package com.bekwam.examples.javafx.oldscores;

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
 * Created by carl_000 on 5/31/2015.
 */
public class SettingsDialog {

    private Logger logger = LoggerFactory.getLogger(ScoresDialog.class);

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SettingsDialog.fxml"));
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
                        mainViewRef.get().openHelpDialog();
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

            try {
                settingsDAO.load();
            } catch(IOException exc) {
                String msg = "Error loading settings from '" + settingsDAO.getAbsolutePath() + "'";
                logger.error( msg, exc );
                Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                alert.showAndWait();
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

    public void setSettingsDAO(SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
    }

    public void setMainViewRef(MainViewController mainView) {
        mainViewRef = new WeakReference<MainViewController>(mainView);
    }

}
