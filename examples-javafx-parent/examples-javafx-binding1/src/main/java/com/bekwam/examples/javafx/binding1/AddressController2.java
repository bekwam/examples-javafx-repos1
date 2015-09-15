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
package com.bekwam.examples.javafx.binding1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX Controller used in the multi-FXML Canada / US address form demo
 * 
 * @see Binding2pp
 * 
 * @author carlwalker
 *
 */
public class AddressController2 extends VBox {

	private Logger logger = LoggerFactory.getLogger( AddressController2.class );

	@FXML
	ToggleButton tbCanada;
	
	@FXML
	ToggleButton tbUS;
	
	@FXML
	StackPane sp;

	private VBox usForm;
	private VBox caForm;
	
	private AddressController_US usController;
	private AddressController_CA caController;
	
	public void postInit() {
		
		usForm.visibleProperty().bind( tbUS.selectedProperty() );
		
		caForm.visibleProperty().bind( tbCanada.selectedProperty() );	
		
		//
		// Makes controls behave like RadioButtons (always one selected)
		//
		tbUS.addEventFilter(EventType.ROOT, (evt) -> {
			
			if( evt.getEventType() == ActionEvent.ACTION ) {
				usController.clearForm();
				caController.clearForm();
			}

			if( tbUS.isSelected() ) {
				evt.consume();				
			}
		});
		
		tbCanada.addEventFilter(EventType.ROOT, (evt) -> {

			if( evt.getEventType() == ActionEvent.ACTION ) {
				usController.clearForm();
				caController.clearForm();
			}
			
			if( tbCanada.isSelected() ) {
				evt.consume();
			} 
		});
	}
	
	@FXML
	public void save(ActionEvent evt) {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[SAVE]");
		}
		
		if( tbCanada.isSelected() ) {

			if( logger.isDebugEnabled() ) {
				logger.debug("[SAVE] addr1={}", caController.tfAddress1.getText());
				logger.debug("[SAVE] addr2={}", caController.tfAddress2.getText());
				logger.debug("[SAVE] city={}", caController.tfCity.getText());
				logger.debug("[SAVE] state={}", caController.cbCAProvince.getSelectionModel().getSelectedItem());
				logger.debug("[SAVE] zip={}", caController.tfPostalCode.getText());
				
			}			

		} else {  // tbUS is selected

			if( logger.isDebugEnabled() ) {
				logger.debug("[SAVE] addr1={}", usController.tfAddress1.getText());
				logger.debug("[SAVE] addr2={}", usController.tfAddress2.getText());
				logger.debug("[SAVE] city={}", usController.tfCity.getText());
				logger.debug("[SAVE] state={}", usController.cbUSState.getSelectionModel().getSelectedItem());
				logger.debug("[SAVE] zip={}", usController.tfPostalCode.getText());
				logger.debug("[SAVE] zip ext={}", usController.tfPostalCodeExt.getText());
			}			


		} 
		((Button)evt.getSource()).getScene().getWindow().hide();
	}
	
	@FXML
	public void cancel(ActionEvent evt) {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[CANCEL]");
		}
		
		((Button)evt.getSource()).getScene().getWindow().hide();
	}
	
	public void setUSForm(VBox usForm, AddressController_US usController) { 
		this.usForm = usForm; 
		this.usController = usController;
		if( !sp.getChildren().contains(this.usForm) ) {
			sp.getChildren().add( usForm );
		}
	}
	
	public void setCAForm(VBox caForm, AddressController_CA caController) { 
		this.caForm = caForm; 
		this.caController = caController;
		if( !sp.getChildren().contains(this.caForm) ) {
			sp.getChildren().add( caForm );
		}
	}
}
