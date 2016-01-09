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
package com.bekwam.examples.javafx.background;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author carl
 *
 */
public class ImprovedBackgroundApp extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/background-fxml/ImprovedBackground.fxml") );
		Parent p = fxmlLoader.load();
		
		ImprovedBackgroundController c = fxmlLoader.getController();
		
		Scene scene = new Scene(p);
		
		primaryStage.setTitle( "ImprovedBackgroundApp" );
		primaryStage.setScene( scene );
		primaryStage.setOnShown( (evt) -> c.startAmination() );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
