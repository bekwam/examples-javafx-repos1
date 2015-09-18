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
 * Main entry point for binding2 demo
 * 
 * A main screen (Address.fxml) will conditional display the Canadian address
 * subform (Address_US.fxml) or the US address subform (Address_CA.fxml)
 * 
 * All the .fxml is loaded with individual FXMLLoader objects.  The two subforms
 * are hooked up to the main controller. There is a postInit() method used for
 * rendering subforms after they are set.
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

		AddressController2 ac2 = new AddressController2();
		
		FXMLLoader loader = new FXMLLoader( getClass().getResource("/binding2-fxml/Address.fxml") );
		loader.setRoot(ac2);
		Parent pAddr = loader.load();
		
		FXMLLoader usLoader = new FXMLLoader( getClass().getResource("/binding2-fxml/Address_US.fxml") );
		usLoader.load();
		
		FXMLLoader caLoader = new FXMLLoader( getClass().getResource("/binding2-fxml/Address_CA.fxml") );
		caLoader.load();
		
		//
		// Setters must be called before postInit()
		//
		((AddressController2)loader.getController()).setUSForm( usLoader.getRoot(), usLoader.getController() );
		((AddressController2)loader.getController()).setCAForm( caLoader.getRoot(), caLoader.getController() );
		((AddressController2)loader.getController()).postInit();
		
		Scene scene = new Scene(pAddr);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle( "Address Form" );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
