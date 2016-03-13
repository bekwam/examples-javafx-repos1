package com.bekwam.examples.javafx.nettyinaction.ch12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EchoClientWS extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(EchoClientWS.class.getResource("/FXEchoClientWS.fxml"));
		
		Parent p = fxmlLoader.load();
		
		Scene scene = new Scene(p);
		
		primaryStage.setScene( scene );
		primaryStage.setTitle("FX Echo Client - WS");
		primaryStage.setWidth( 320 );
		primaryStage.setHeight(568);
		
		primaryStage.show();
	}
}
