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
package com.bekwam.examples.javafx.plugin.mainapp;

import java.net.URL;
import java.net.URLClassLoader;

import com.bekwam.examples.javafx.plugin.framework.Plugin;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author carl
 *
 */
public class MainApp extends Application {

	private final ObservableList<Plugin> plugins = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//
		// Check for plugins
		//
		String pathToPlugin = 
				"/home/carl/.m2/repository/com/bekwam/examples-javafx-plugin-someplugin/1.0-SNAPSHOT/examples-javafx-plugin-someplugin-1.0-SNAPSHOT.jar";
		
		// on mac /Users/carlwalker/.m2/repository/com/bekwam/examples-javafx-plugin-someplugin/1.0-SNAPSHOT/examples-javafx-plugin-someplugin-1.0-SNAPSHOT.jar
		
		String pluginClass = 
				"com.bekwam.examples.javafx.plugin.someplugin.SomePlugin";

		VBox vbox = new VBox();
		
		Label label = new Label("JAR");
		TextField tfJarFile = new TextField();
		tfJarFile.setText( pathToPlugin );
		
		Label label_cl = new Label("Class");
		TextField tfClass = new TextField();
		tfClass.setText( pluginClass );
		
		Button load_b = new Button("Load Plugin");
		load_b.setOnAction((evt) -> {
			
			try {
				URL url = new URL("jar:file:" + tfJarFile.getText() + "!/");
			
				ClassLoader cl = new URLClassLoader( new URL[]{ url } );

				final Plugin plugin = (Plugin)Class.forName(tfClass.getText(), true, cl).newInstance();
			
				plugin.init();
				
				plugins.add( plugin );
				
			} catch(Exception exc) {
				exc.printStackTrace();
			}
		});
		
		ChoiceBox<Plugin> cb = new ChoiceBox<>();
		cb.itemsProperty().bind( new SimpleObjectProperty<ObservableList<Plugin>>(plugins) );
		
		Button b = new Button("Run Plugin");
		b.setOnAction( (evt) -> cb.getSelectionModel().getSelectedItem().operate() );
		
		vbox.setPadding(new Insets(40.0d));
		vbox.getChildren().addAll( label, tfJarFile, label_cl, tfClass, load_b, new Separator(), cb, b );
		
		Scene scene = new Scene(vbox, 1024, 768);
		
		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
