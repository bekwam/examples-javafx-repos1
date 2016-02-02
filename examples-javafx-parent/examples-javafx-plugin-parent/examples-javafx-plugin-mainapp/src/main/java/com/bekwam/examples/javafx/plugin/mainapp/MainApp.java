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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author carl
 *
 */
public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//
		// Check for plugins
		//
		String pathToPlugin = 
				"/home/carl/.m2/repository/com/bekwam/examples-javafx-plugin-someplugin/1.0-SNAPSHOT/examples-javafx-plugin-someplugin-1.0-SNAPSHOT.jar";
		
		String pluginClass = 
				"com.bekwam.examples.javafx.plugin.someplugin.SomePlugin";

		URL url = new URL("jar:file:" + pathToPlugin + "!/");
		
		ClassLoader cl = new URLClassLoader( new URL[]{ url } );

		final Plugin plugin = (Plugin)Class.forName(pluginClass, true, cl).newInstance();
		
		plugin.init();
		
		VBox vbox = new VBox();
		
		Button b = new Button("Run Plugin");
		b.setOnAction( (evt) -> plugin.operate() );
		
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add( b );
		
		Scene scene = new Scene(vbox, 1024, 768);
		
		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
