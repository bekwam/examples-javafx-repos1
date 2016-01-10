/*
 * Copyright 2016 Bekwam, Inc
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
package com.bekwam.examples.javafx.table;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author carl_000
 */
public class TableApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

    	Injector injector = Guice.createInjector(new TableAppModule());
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(
    			TableApp.class.getResource("/Persons.fxml"),
    			null,
    			new JavaFXBuilderFactory(),
    			new Callback<Class<?>, Object>() {
    				@Override
    	    		public Object call(Class<?> clazz) {
    					return injector.getInstance(clazz);
    				}
    			});
    	
        Parent p = fxmlLoader.load();
        
        PersonsController pc = fxmlLoader.getController();
        
        Scene scene = new Scene(p);
        
        scene.setOnKeyPressed((evt) -> {
        	if( evt.getCode() == KeyCode.DELETE ) {
        		pc.deletePersons();
        	} else if( evt.getCode() == KeyCode.INSERT ) {
        		pc.addPerson();
        	}
        });

        primaryStage.setTitle( "Person Table App");
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
