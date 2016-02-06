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

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
	ListView<Path> lvSubApps;
	
	@FXML
	ListView<File> lvJARs;
	
	@Inject
	@Named("SubAppFolder")
	File subappFolder;

	@Inject
	@Named("SubAppJars")
	ObservableList<Path> subappJars;

	@Inject
	@Named("StartupCommandsFullPath")
	String startupCommandsFullPath;

	@FXML
	public void initialize() {

		lvSubApps.itemsProperty().bind( new SimpleObjectProperty<>(subappJars) );

	}

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
			
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						for( File sf : selectedFiles ) {

							Path sf_p = sf.toPath();
							Path target_p = Paths.get(subappFolder.getAbsolutePath(), sf.getName());

							if (logger.isDebugEnabled()) {
								logger.debug("[IMPORT] importing " + sf_p + " to " + target_p);
							}

							//
							// It would be a good idea to validate a signature here to
							// prove that the code is coming from a known source.
							//
							// Also, SubApp validation should be here to make sure that
							// the JAR is a valid SubApp.
							//

							Files.copy(sf_p, target_p, StandardCopyOption.REPLACE_EXISTING);

							Platform.runLater( () -> {
								subappJars.add( target_p );
							});
						}

						return null;
					}

					@Override
					protected void failed() {
						super.failed();
						logger.error( "error importing", getException());

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Import Error");
						alert.setHeaderText( getException().getClass().getName() );
						alert.setContentText( getException().getMessage() );
						alert.showAndWait();

					}
				};
				new Thread(task).start();
			}
		}

	@FXML
	public void uninstall() {

		List<Path> selectedFiles = lvSubApps.getSelectionModel().getSelectedItems();
		if (selectedFiles == null || selectedFiles.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Uninstall");
			alert.setHeaderText("Select JARs");
			alert.setContentText("No JAR files selected to uninstall");
			alert.showAndWait();
		} else {

			Alert conf = new Alert(AlertType.CONFIRMATION);
			conf.setTitle("Uninstall");
			conf.setHeaderText("Uninstall JARs?");

			String ct = "";
			for( Path sp : selectedFiles ) {
				ct += sp.getFileName();
				ct += "\n";
			}
			conf.setContentText( ct );

			conf.showAndWait()
					.filter(response -> response == ButtonType.OK)
					.ifPresent(response ->	{

						Task<Void> task = new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								for( Path sf : selectedFiles ) {

									//
									// Delete command is deferred b/c holding on to file handled since it was
									// loaded
									//
									try (
											BufferedWriter bw = new BufferedWriter(new FileWriter(startupCommandsFullPath, true))
											) {
										bw.write("D " + sf.toAbsolutePath().toString());
										bw.newLine();

									} catch(IOException exc) {
										logger.error("can't write command to file " + startupCommandsFullPath, exc);
									}

									Platform.runLater( () -> subappJars.remove(sf) );
								}
								return null;
							}

							@Override
							protected void succeeded() {
								super.succeeded();
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Uninstall");
								alert.setHeaderText("Uninstall Successful");
								alert.setContentText("You need to restart the app for the install to take effect.");
								alert.showAndWait();
							}

							@Override
							protected void failed() {
								super.failed();
								logger.error( "delete task failed", getException());
							}
						};
						new Thread(task).start();
					}
			);

		}
	}
}
