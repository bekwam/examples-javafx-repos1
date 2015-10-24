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
package com.bekwam.examples.javafx.userdata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author carl_000
 */
public class UserDataMain extends Application {

    private Logger log = LoggerFactory.getLogger( UserDataMain.class );

    public void start(Stage primaryStage) {

        try {
            Parent p = FXMLLoader.load(UserDataMain.class.getResource("/fxml/List.fxml"));

            Scene scene = new Scene(p);

            primaryStage.setTitle("User Data Main");
            primaryStage.setScene( scene );
            primaryStage.show();

        } catch(IOException exc) {
            log.error( "could not load fxml file '/fxml/List.fxml", exc );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
