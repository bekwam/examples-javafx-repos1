package com.bekwam.examples.javafx.macmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

public class MacMenuController {

	@FXML
	VBox vbox;
	
	@FXML
	MenuBar menubar;
	
	@FXML
	public void initialize() {
		
		String macMenu = System.getProperty("apple.laf.useScreenMenuBar");
		
		if( macMenu != null && macMenu == "true" ) {
			vbox.getChildren().remove(menubar);
		}
	}
	
	@FXML
	public void close() {
		Platform.exit();
	}
}
