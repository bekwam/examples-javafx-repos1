package com.bekwam.examples.javafx.nettyinaction.ch2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EchoClient extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(EchoClient.class.getResource("/FXEchoClient.fxml"));
		
		Parent p = fxmlLoader.load();
		
		Scene scene = new Scene(p);
		
		primaryStage.setScene( scene );
		primaryStage.setTitle("FX Echo Client");
		primaryStage.setWidth( 320 );
		primaryStage.setHeight(568);
		
		primaryStage.show();
	}
}
