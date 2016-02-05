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
package com.bekwam.examples.javafx.dynamic.app_core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * JavaFX Controller for Preferences screen
 * 
 * @author carl
 *
 */
public class PreferencesController {

    private Logger logger = LoggerFactory.getLogger(PreferencesController.class);

    @FXML
	TextField tfJARFolder;
	
	@FXML
	ListView<SubAppItem> lvSubApps;
	
	@FXML
	ListView<File> lvJARs;
	
	@Inject
	@Named("SubAppFolder")
	File subappFolder;
	
	@FXML
	public void browse(ActionEvent evt) {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[BROWSE]");
		}
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select JARs");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("JAR Files", "*.jar", "*.zip")
						);
		List<File> selectedFiles = 
				fileChooser.showOpenMultipleDialog(((Button)evt.getSource()).getScene().getWindow());
		if( selectedFiles != null ) {
			lvJARs.getItems().clear();
			lvJARs.setItems( FXCollections.observableArrayList(selectedFiles) );
		}
	}
	
	@FXML
	public void importJARs() {

		if( logger.isDebugEnabled() ) {
			logger.debug("[IMPORT] importing into " + subappFolder.getName());
		}

		List<File> selectedFiles = lvJARs.getSelectionModel().getSelectedItems();
		if( selectedFiles == null || selectedFiles.isEmpty() ) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Import");
			alert.setHeaderText("Select JARs");
			alert.setContentText("No JAR files selected for import");
			alert.showAndWait();
		} else {
			
			for( File sf : selectedFiles ) {
				
				Path sf_p = sf.toPath();
				Path target_p = Paths.get(subappFolder.getAbsolutePath(), sf.getName());
				
				if( logger.isDebugEnabled() ) {
					logger.debug("[IMPORT] importing " + sf_p + " to " + target_p);					
				}
				
				try {
					
					//
					// It would be a good idea to validate a signature here to
					// prove that the code is coming from a known source.
					// 
					// Also, SubApp validation should be here to make sure that
					// the JAR is a valid SubApp.
					//
					
					Files.copy(sf_p,  target_p, StandardCopyOption.REPLACE_EXISTING);
					
				} catch(Exception exc) {
					logger.error( "error copying " + sf_p + " to " + target_p, exc);
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Import");
					alert.setHeaderText("Import Error");
					alert.setContentText( "Error copying " + sf_p + " to " + target_p );
					alert.showAndWait();
					break;
				}
			}
		}
	}
	
	@FXML
	public void uninstall() {
		
	}
}
