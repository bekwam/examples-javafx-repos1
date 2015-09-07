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
package com.bekwam.examples.javafx.waitnotify;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author carl_000
 */
public class WaitNotifyApp extends Application {

    private Logger logger = LoggerFactory.getLogger( WaitNotifyApp.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        if( logger.isDebugEnabled() ) {
            logger.debug("[START]");
        }

        VBox vbox = new VBox(20.0d);
        vbox.setPadding(new Insets(10.0d));

        Button btn = new Button("Launch Dialog");
        btn.setOnAction((evt) -> {

            GetDataDialog dialog = new GetDataDialog();
            dialog.init();
            Scene dialogScene = new Scene(dialog.getRoot());
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(dialogScene);
            dialogStage.showAndWait();

            if (logger.isDebugEnabled()) {
                logger.debug("[BTN] data=" + dialog.getData());
            }
        });

        Button btnTask = new Button("Launch Dialog As Task");
        btnTask.setOnAction( (evt) -> {

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {

                    GetDataDialog dialog = new GetDataDialog();

                    Platform.runLater( () -> {
                        dialog.init();
                        Scene dialogScene = new Scene(dialog.getRoot());
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.APPLICATION_MODAL);
                        dialogStage.setScene(dialogScene);
                        dialogStage.showAndWait();
                    });

                    if(logger.isDebugEnabled()) {
                        logger.debug("[BTN TASK] data=" + dialog.getData());
                    }

                    return null;
                }
            };
            new Thread(task).start();
        });

        Button btnTaskSynced = new Button("Launch Dialog As Task (Synched)");
        btnTaskSynced.setOnAction( (evt) -> {

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {

                    GetDataDialogSynced dialog = new GetDataDialogSynced();

                    Platform.runLater( () -> {
                        dialog.init();
                        Scene dialogScene = new Scene(dialog.getRoot());
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.APPLICATION_MODAL);
                        dialogStage.setScene(dialogScene);
                        dialogStage.showAndWait();
                    });

                    synchronized (dialog) {
                        dialog.wait( 10000 );
                    }

                    if(logger.isDebugEnabled()) {
                        logger.debug("[BTN TASK] data=" + dialog.getData());
                    }

                    return null;
                }
            };
            new Thread(task).start();
        });

        vbox.getChildren().addAll(btn, btnTask, btnTaskSynced);

        Scene scene = new Scene(vbox);

        primaryStage.setScene( scene );
        primaryStage.show();
    }
}
