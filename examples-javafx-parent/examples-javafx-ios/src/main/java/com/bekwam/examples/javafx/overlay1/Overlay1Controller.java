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
package com.bekwam.examples.javafx.overlay1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main JavaFX Controller for Overlay1App
 *
 * @author carl_000
 */
public class Overlay1Controller {

    private Logger logger = LoggerFactory.getLogger(Overlay1Controller.class);

    @FXML
    StackPane sp;

    @FXML
    VBox mainContent;

    @FXML
    VBox overlayContent;

    @FXML
    VBox overlayBottom;

    @FXML
    Line overlayBottomLineFlat;

    private Polyline overlayBottomLineUp;
    private boolean overlayShowing = false;
    private Border topHighlightBorder;
    private Border topEmptyBorder;
    private double lastYPress = 0.0d;

    public void initialize() {

        createTopHighlightBorder();
        createTopEmptyBorder();
        createOverlayBottomLineUp();

        double screenHeight = sp.getPrefHeight();

        overlayContent.setTranslateY(-1 * screenHeight);

        /**
         * Record lastYPress to determine whether or not the drag is allowed.
         * Drag down is only allowed at the top of the screen.
         */
        sp.addEventFilter(MouseEvent.MOUSE_PRESSED, (evt) -> lastYPress = evt.getY());

        /**
         * If at the top of the screen, provide a white border visual cue that a
         * drag down is available.
         */
        sp.addEventFilter(MouseEvent.MOUSE_MOVED, (evt) -> {
            if( evt.getY() <= 20.0d && !overlayShowing ) {
                if( mainContent.getBorder() != topHighlightBorder ) {
                    mainContent.setBorder(topHighlightBorder);
                }
            } else {
                if( mainContent.getBorder() != topEmptyBorder ) {
                    mainContent.setBorder(topEmptyBorder);
                }
            }
        });

        /**
         * Until the end of the screen -- and if the drag down is within bounds --
         * keep moving the overlay down on top of the main.
         */
        sp.addEventFilter(MouseEvent.MOUSE_DRAGGED, (evt) -> {

            if( lastYPress > 20.0d && !overlayShowing )
                return;

            if (evt.getY() <= sp.getPrefHeight()) {  // don't run off the screen
                double h = -1 * (sp.getPrefHeight() - evt.getY());
                overlayContent.setTranslateY(h);
            }
        });

        /**
         * If the mouse is released (and a drag is in effect), decide whether or not
         * to show the whole overlay or hide it again.  The criteria is if the drag
         * the midpoint passes of the screen.
         */
        sp.addEventFilter(MouseEvent.MOUSE_RELEASED, (evt) -> {

            if( lastYPress > 20.0d && !overlayShowing )
                return;

            if (evt.getY() > sp.getPrefHeight() / 2) {

                switchToOverlayBottomLineUp();

                if (logger.isDebugEnabled()) {
                    logger.debug("[RELEASED] filling full screen; mouse.y={}", evt.getY());
                }

                double startTranslateY = -1 * (screenHeight - evt.getY());
                double endTranslateY = 0.0d;

                if (evt.getY() <= sp.getPrefHeight()) {  // not off the screen
                    animateOverlayContent(startTranslateY, endTranslateY);
                } else { // off screen - just do a simple set
                    overlayContent.setTranslateY(endTranslateY);
                }

                overlayShowing = true;

            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("[RELEASED] resetting to 0; mouse.y={}", evt.getY());
                }

                switchToOverlayBottomFlat();

                double startTranslateY = -1 * (screenHeight - evt.getY());
                double endTranslateY = -1 * screenHeight;

                if (logger.isDebugEnabled()) {
                    logger.debug("[RELEASED] animating from {} to {}", startTranslateY, endTranslateY);
                }

                animateOverlayContent(startTranslateY, endTranslateY);

                overlayShowing = false;
            }
        });
    }

    private void createTopHighlightBorder() {
        Stop[] stops = new Stop[] {
                new Stop(0, Color.WHITE),
                new Stop(.3, Color.LIGHTGRAY),
                new Stop(1, Color.TRANSPARENT)
        };
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

        topHighlightBorder =
                new Border(new BorderStroke(
                        lg1, null, null, null,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY,
                        new BorderWidths( 8.0d ),
                        null
                ));
    }

    private void createOverlayBottomLineUp() {
        overlayBottomLineUp = new Polyline();
        overlayBottomLineUp.getPoints().addAll(
                new Double[]{
                        -10.0d, 4.0d,
                        0.0d, 0.0d,
                        10.0d, 4.0d}
        );
    }

    private void createTopEmptyBorder() {
        topEmptyBorder =
                new Border(new BorderStroke(
                        null, null, null, null,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY,
                        null,
                        null
                ));
    }

    private void switchToOverlayBottomLineUp() {
        if( !overlayBottom.getChildren().contains(overlayBottomLineUp)) {
            overlayBottom.getChildren().remove(overlayBottomLineFlat);
            overlayBottom.getChildren().add(overlayBottomLineUp);
        }
    }

    private void switchToOverlayBottomFlat() {
        if( !overlayBottom.getChildren().contains(overlayBottomLineFlat)) {
            overlayBottom.getChildren().remove(overlayBottomLineUp);
            overlayBottom.getChildren().add(overlayBottomLineFlat);
        }
    }

    private void animateOverlayContent(double startTranslateY, double endTranslateY) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(overlayContent.translateYProperty(), startTranslateY)
                ),
                new KeyFrame(Duration.millis(300.0d),
                        new KeyValue(overlayContent.translateYProperty(), endTranslateY)
                )
        );
        timeline.play();
    }

}
