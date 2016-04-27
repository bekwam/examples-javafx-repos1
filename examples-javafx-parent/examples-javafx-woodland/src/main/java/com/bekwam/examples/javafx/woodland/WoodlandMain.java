package com.bekwam.examples.javafx.woodland;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WoodlandMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(WoodlandMain.class.getResource("/fxml/Woodland.fxml"));
		
		Parent p = fxmlLoader.load();
		
		Scene scene = new Scene( p );
		scene.getStylesheets().add( "css/woodland.css" );
		
		primaryStage.setTitle("Woodland");
		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public static void main(String[] args) { launch(args); }
}
