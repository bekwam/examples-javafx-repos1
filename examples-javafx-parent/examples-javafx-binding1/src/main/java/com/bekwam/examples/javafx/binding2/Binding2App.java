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
package com.bekwam.examples.javafx.binding2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for binding1 demo
 * 
 * @author carlwalker
 *
 */
public class Binding2App extends Application {

	private Logger logger = LoggerFactory.getLogger( Binding2App.class );
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[START]");
		}

		Parent p = FXMLLoader.load( getClass().getResource("/binding2-fxml/Preferences.fxml"));
		
		Scene scene = new Scene(p);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle( "Preferences Form" );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
