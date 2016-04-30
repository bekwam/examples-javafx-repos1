package com.bekwam.examples.javafx.wizard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WizardMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent p = FXMLLoader.load( WizardMain.class.getResource("/wizard-fxml/Wizard.fxml") );
		
		Scene scene = new Scene(p);
		
		primaryStage.setScene( scene );
		primaryStage.setWidth( 800 );
		primaryStage.setHeight( 600 );
		primaryStage.setTitle("Wizard");
		
		primaryStage.show();
	}
	
	public static void main(String[] args) { launch(args); }

}
