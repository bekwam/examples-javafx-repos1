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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Controller for Scores screen
 *
 * @author carl_000
 */
public class ScoresDialogController extends VBox {

    private final Logger logger = LoggerFactory.getLogger(ScoresDialog.class);

    @FXML
    TextField txtVerbalScore1995;

    @FXML
    TextField txtVerbalScoreRecentered;

    @FXML
    Label errMsgVerbal1995;

    @FXML
    Label errMsgVerbalRecentered;

    @FXML
    TextField txtMathScore1995;

    @FXML
    TextField txtMathScoreRecentered;

    @FXML
    Label errMsgMath1995;

    @FXML
    Label errMsgMathRecentered;

    @FXML
    Label lblVerbalScoresGroup;

    private RecenteredDAO dao;
    private SettingsDAO settingsDAO;
    private WeakReference<MainViewController> mainViewRef;

    @FXML
    public void initialize() {


        EventHandler<KeyEvent> f1KeyHandler =  (evt) -> {

            if (evt.getCode().equals(KeyCode.F1)) {
                try {
                    if (logger.isDebugEnabled()) {
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
        };

        txtVerbalScore1995.addEventHandler(KeyEvent.KEY_PRESSED, f1KeyHandler);
        txtVerbalScoreRecentered.addEventHandler(KeyEvent.KEY_PRESSED, f1KeyHandler);
        txtMathScore1995.addEventHandler(KeyEvent.KEY_PRESSED, f1KeyHandler);
        txtMathScoreRecentered.addEventHandler(KeyEvent.KEY_PRESSED, f1KeyHandler);

    }

    public void reset() {
        txtVerbalScore1995.setText("");
        txtVerbalScoreRecentered.setText("");
        txtMathScore1995.setText("");
        txtMathScoreRecentered.setText("");
        resetErrMsgs();
    }

    @FXML
    public void updateVerbalRecentered() {
        if (logger.isDebugEnabled()) {
            logger.debug("[UPD V RECENTERED]");
        }

        if( dao == null ) {
            throw new IllegalArgumentException("dao has not been set; call setRecenteredDAO() before calling this method");
        }

        String score1995_s = txtVerbalScore1995.getText();

        if(StringUtils.isNumeric(score1995_s) ) {
            Integer score1995 = NumberUtils.toInt(score1995_s);

            if( withinRange(score1995) ) {

                if( needsRound(score1995) ) {
                    score1995 = round(score1995);
                    txtVerbalScore1995.setText( String.valueOf(score1995));
                }

                resetErrMsgs();
                Integer scoreRecentered = dao.lookupRecenteredVerbalScore(score1995);
                txtVerbalScoreRecentered.setText(String.valueOf(scoreRecentered));
            } else {
                errMsgVerbal1995.setVisible(true);
            }
        } else {
            errMsgVerbal1995.setVisible(true);
        }
    }

    @FXML
    public void updateVerbal1995() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[UPD V 1995]");
        }

        if( dao == null ) {
            throw new IllegalArgumentException("dao has not been set; call setRecenteredDAO() before calling this method");
        }

        String recenteredScore_s = txtVerbalScoreRecentered.getText();

        if(StringUtils.isNumeric(recenteredScore_s) ) {
            Integer scoreRecentered = NumberUtils.toInt(recenteredScore_s);
            if( withinRange(scoreRecentered) ) {

                if( needsRound(scoreRecentered) ) {
                    scoreRecentered = round(scoreRecentered);
                    txtVerbalScoreRecentered.setText( String.valueOf(scoreRecentered));
                }

                resetErrMsgs();
                Integer score1995 = dao.lookup1995VerbalScore(scoreRecentered);
                txtVerbalScore1995.setText(String.valueOf(score1995));
            } else {
                errMsgVerbalRecentered.setVisible(true);
            }
        } else {
            errMsgVerbalRecentered.setVisible(true);
        }
    }


    @FXML
    public void updateMathRecentered() {
        if (logger.isDebugEnabled()) {
            logger.debug("[UPD M RECENTERED]");
        }

        if( dao == null ) {
            throw new IllegalArgumentException("dao has not been set; call setRecenteredDAO() before calling this method");
        }

        String score1995_s = txtMathScore1995.getText();

        if(StringUtils.isNumeric(score1995_s) ) {
            Integer score1995 = NumberUtils.toInt(score1995_s);

            if( withinRange(score1995) ) {

                if( needsRound(score1995) ) {
                    score1995 = round(score1995);
                    txtMathScore1995.setText( String.valueOf(score1995));
                }

                resetErrMsgs();
                Integer scoreRecentered = dao.lookupRecenteredMathScore(score1995);
                txtMathScoreRecentered.setText(String.valueOf(scoreRecentered));
            } else {
                errMsgMath1995.setVisible(true);
            }
        } else {
            errMsgMath1995.setVisible(true);
        }
    }

    @FXML
    public void updateMath1995() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[UPD 1995]");
        }

        if( dao == null ) {
            throw new IllegalArgumentException("dao has not been set; call setRecenteredDAO() before calling this method");
        }

        String recenteredScore_s = txtMathScoreRecentered.getText();

        if(StringUtils.isNumeric(recenteredScore_s) ) {
            Integer scoreRecentered = NumberUtils.toInt(recenteredScore_s);
            if( withinRange(scoreRecentered) ) {

                if( needsRound(scoreRecentered) ) {
                    scoreRecentered = round(scoreRecentered);
                    txtMathScoreRecentered.setText( String.valueOf(scoreRecentered));
                }

                resetErrMsgs();
                Integer score1995 = dao.lookup1995MathScore(scoreRecentered);
                txtMathScore1995.setText(String.valueOf(score1995));
            } else {
                errMsgMathRecentered.setVisible(true);
            }
        } else {
            errMsgMathRecentered.setVisible(true);
        }
    }

    @FXML
    public void resetErrMsgs() {
        errMsgVerbalRecentered.setVisible(false);
        errMsgVerbal1995.setVisible(false);
        errMsgMathRecentered.setVisible(false);
        errMsgMath1995.setVisible(false);
    }

    private boolean withinRange(Integer score) {
        if( score != null && score >= 200 && score <= 800 ) {
            return true;
        }
        return false;
    }

    @FXML
    public void close(ActionEvent evt) {

        Scene scene = ((Button)evt.getSource()).getScene();
        if( scene != null ) {
            Window w = scene.getWindow();
            if (w != null) {
                w.hide();
            }
        }
    }

    public void setDao(RecenteredDAO dao) {
        this.dao = dao;
    }

    public void setSettingsDAO(SettingsDAO settingsDAO) { this.settingsDAO = settingsDAO; }

    public void setMainViewRef(MainViewController mainView) {
        mainViewRef = new WeakReference<>(mainView);
    }

    private boolean needsRound(Integer score) {
        return !((score%10)==0);
    }

    private Integer round(Integer score) {
        Integer tens = score / 10;
        Integer retval = tens * 10;  // strips ones
        if( settingsDAO.getRoundUp() ) {
            retval = (tens+1) * 10;  // shift, add 1 to tens
        }
        return retval;
    }
}
