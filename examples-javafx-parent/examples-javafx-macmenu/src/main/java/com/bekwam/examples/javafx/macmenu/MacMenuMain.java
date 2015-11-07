package com.bekwam.examples.javafx.macmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MacMenuMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.setProperty("apple.laf.useScreenMenuBar", "false");

		FXMLLoader fxmlLoader = new FXMLLoader( this.getClass().getResource("/macmenu-fxml/MacMenu.fxml") );
		fxmlLoader.load();
		
		Parent p = fxmlLoader.getRoot();
		
		Scene scene = new Scene(p);
		
		primaryStage.setTitle( "Mac Menu" );
		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
