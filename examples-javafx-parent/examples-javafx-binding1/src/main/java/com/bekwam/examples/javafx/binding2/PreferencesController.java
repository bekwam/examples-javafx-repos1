/*
 * Copyright 2015 Bekwam, Inc
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
package com.bekwam.examples.javafx.binding2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

public class PreferencesController {

	private Logger logger = LoggerFactory.getLogger(PreferencesController.class);
	
	@FXML
	TableColumn<Soda, String> tcSoda;
	
	@FXML
	TableView<Soda> tblSodas;
	
	@FXML
	CheckBox chkSelectAll;
	
	@FXML
	CheckBox chkDeselectAll;
	
	@FXML
	public void initialize() {
		
        tcSoda.setCellValueFactory(
                new PropertyValueFactory<Soda, String>("name")
        );

        tblSodas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        tblSodas.getItems().add( new Soda("Coke") );
        tblSodas.getItems().add( new Soda("Pepsi") );
        tblSodas.getItems().add( new Soda("7-Up") );
        
        chkSelectAll.addEventFilter( MouseEvent.MOUSE_RELEASED, (evt) -> {
        	selectAll();
        	evt.consume();
        });

        chkSelectAll.addEventFilter( TouchEvent.TOUCH_RELEASED, (evt) -> {
        	selectAll();
        	evt.consume();
        });
        
        chkDeselectAll.addEventFilter( MouseEvent.MOUSE_RELEASED, (evt) -> {
        	deselectAll();
        	evt.consume();
        });

        chkDeselectAll.addEventFilter( TouchEvent.TOUCH_RELEASED, (evt) -> {
        	deselectAll();
        	evt.consume();
        });
	}
	
	private void selectAll() {
		if( logger.isDebugEnabled() ) {
			logger.debug("[SELECT ALL]");
		}
		tblSodas.getSelectionModel().selectAll();
	}
	
	private void deselectAll() {
		if( logger.isDebugEnabled() ) {
			logger.debug("[DESELECT ALL]");
		}
		tblSodas.getSelectionModel().clearSelection();
	}

	@FXML
	public void save(ActionEvent evt) {
		if( logger.isDebugEnabled() ) {
			tblSodas.getSelectionModel().getSelectedItems()
				.stream()
				.forEach( (s) -> logger.debug("[SAVE] saving {}", s.getName()) );
		}
		((Button)evt.getSource()).getScene().getWindow().hide();
	}
	
	@FXML
	public void cancel(ActionEvent evt) {
		((Button)evt.getSource()).getScene().getWindow().hide();
	}
}
