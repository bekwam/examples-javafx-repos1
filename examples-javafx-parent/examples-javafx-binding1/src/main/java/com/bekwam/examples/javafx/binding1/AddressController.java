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

import java.util.ArrayList;
import java.util.List;

import com.bekwam.examples.javafx.commons.PairStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.Pair;

/**
 * JavaFX Controller used in the single-FXML Canada / US address form demo
 * 
 * @see Binding1App
 * 
 * @author carlwalker
 *
 */
public class AddressController {

	private Logger logger = LoggerFactory.getLogger( AddressController.class );

	@FXML
	ToggleButton tbCanada;
	
	@FXML
	ToggleButton tbUS;
	
	@FXML
	TextField tfAddress1;
	
	@FXML
	TextField tfAddress2;

	@FXML
	TextField tfCity;

	@FXML
	Label lblCAProvince;
	
	@FXML
	ChoiceBox<Pair<String, String>> cbCAProvince;
	
	@FXML
	Label lblUSState;
	
	@FXML
	ChoiceBox<Pair<String, String>> cbUSState;
	
	@FXML
	TextField tfPostalCode;
	
	@FXML
	TextField tfPostalCodeExt;
	
	@FXML
	Label lblPostalCodeSep;
	
	private final List<Pair<String, String>> provinces = new ArrayList<>();
	private final List<Pair<String, String>> states = new ArrayList<>();

	public AddressController() {
		initCanadianProvinces();
		initUSStates();
	}
	
	@FXML
	public void initialize() {		
		
		//
		// Controls specific to Canada
		//
		lblCAProvince.visibleProperty().bind( tbCanada.selectedProperty() );
		
		cbCAProvince.visibleProperty().bind( tbCanada.selectedProperty() );
		cbCAProvince.setItems( FXCollections.observableArrayList(provinces) );
		cbCAProvince.setConverter(new PairStringConverter(provinces));
		
		//
		// Controls specific to US
		//
		lblUSState.visibleProperty().bind( tbUS.selectedProperty() );
		
		cbUSState.visibleProperty().bind( tbUS.selectedProperty() );
		cbUSState.setItems( FXCollections.observableArrayList(states) );
		cbUSState.setConverter(new PairStringConverter(states));
		
		tfPostalCodeExt.visibleProperty().bind(tbUS.selectedProperty());
		
		lblPostalCodeSep.visibleProperty().bind(tbUS.selectedProperty());		
	}
	
	@FXML
	public void clearForm() {
		tfAddress1.setText("");
		tfAddress2.setText("");
		tfCity.setText("");
		cbCAProvince.getSelectionModel().clearSelection();
		cbUSState.getSelectionModel().clearSelection();
		tfPostalCode.setText("");
		tfPostalCodeExt.setText("");;
	}
	
	@FXML
	public void save(ActionEvent evt) {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[SAVE]");
		}
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[SAVE] address1={}", tfAddress1.getText());
			logger.debug("[SAVE] address2={}", tfAddress2.getText());
			logger.debug("[SAVE] city={}", tfCity.getText());
			logger.debug("[SAVE] ca province={}", cbCAProvince.getSelectionModel().getSelectedItem());
			logger.debug("[SAVE] us state={}", cbUSState.getSelectionModel().getSelectedItem());
			logger.debug("[SAVE] postalCode={}", tfPostalCode.getText());
			logger.debug("[SAVE] postalCodeExt={}", tfPostalCodeExt.getText());
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

	private void initCanadianProvinces() {
		provinces.add(new Pair<String, String>("AB", "Alberta"));
		provinces.add(new Pair<String, String>("BC", "British Columbia"));
		provinces.add(new Pair<String, String>("MB", "Manitoba"));
		provinces.add(new Pair<String, String>("NB", "New Brunswick"));
		provinces.add(new Pair<String, String>("NL", "Newfoundland and Labrador"));
		provinces.add(new Pair<String, String>("NS", "Nova Scotia"));
		provinces.add(new Pair<String, String>("NT", "Northwest Territories"));
		provinces.add(new Pair<String, String>("NU", "Nunavut"));
		provinces.add(new Pair<String, String>("ON", "Ontario"));
		provinces.add(new Pair<String, String>("PE", "Prince Edward Island"));
		provinces.add(new Pair<String, String>("QC", "Quebec"));
		provinces.add(new Pair<String, String>("SK", "Saskatchewan"));
		provinces.add(new Pair<String, String>("YT", "Yukon"));
	}
	
	private void initUSStates() {
		states.add(new Pair<String, String>("AL", "Alabama"));
		states.add(new Pair<String, String>("AK", "Alaska"));
		states.add(new Pair<String, String>("AZ", "Arizona"));
		states.add(new Pair<String, String>("AR", "Arkansas"));
		states.add(new Pair<String, String>("CA", "California"));
		states.add(new Pair<String, String>("CO", "Colorado"));
		states.add(new Pair<String, String>("CT", "Connecticut"));
		states.add(new Pair<String, String>("DE", "Delaware"));
		states.add(new Pair<String, String>("DC", "Dist. of Columbia"));
		states.add(new Pair<String, String>("FL", "Florida"));
		states.add(new Pair<String, String>("GA", "Georgia"));
		states.add(new Pair<String, String>("HI", "Hawaii"));
		states.add(new Pair<String, String>("ID", "Idaho"));
		states.add(new Pair<String, String>("IL", "Illinois"));
		states.add(new Pair<String, String>("IN", "Indiana"));
		states.add(new Pair<String, String>("IA", "Iowa"));
		states.add(new Pair<String, String>("KS", "Kansas"));
		states.add(new Pair<String, String>("KY", "Kentucky"));
		states.add(new Pair<String, String>("LA", "Louisiana"));
		states.add(new Pair<String, String>("ME", "Maine"));
		states.add(new Pair<String, String>("MD", "Maryland"));
		states.add(new Pair<String, String>("MA", "Massachusetts"));
		states.add(new Pair<String, String>("MI", "Michigan"));
		states.add(new Pair<String, String>("MN", "Minnesota"));
		states.add(new Pair<String, String>("MS", "Mississippi"));
		states.add(new Pair<String, String>("MO", "Missouri"));
		states.add(new Pair<String, String>("MT", "Montana"));
		states.add(new Pair<String, String>("NE", "Nebraska"));
		states.add(new Pair<String, String>("NV", "Nevada"));
		states.add(new Pair<String, String>("NH", "New Hampshire"));
		states.add(new Pair<String, String>("NJ", "New Jersey"));
		states.add(new Pair<String, String>("NM", "New Mexico"));
		states.add(new Pair<String, String>("NY", "New York"));
		states.add(new Pair<String, String>("NC", "North Carolina"));
		states.add(new Pair<String, String>("ND", "North Dakota"));
		states.add(new Pair<String, String>("OH", "Ohio"));
		states.add(new Pair<String, String>("OK", "Oklahoma"));
		states.add(new Pair<String, String>("OR", "Oregon"));
		states.add(new Pair<String, String>("PA", "Pennsylvania"));
		states.add(new Pair<String, String>("RI", "Rhode Island"));
		states.add(new Pair<String, String>("SC", "South Carolina"));
		states.add(new Pair<String, String>("SD", "South Dakota"));
		states.add(new Pair<String, String>("TN", "Tennessee"));
		states.add(new Pair<String, String>("TX", "Texas"));
		states.add(new Pair<String, String>("UT", "Utah"));
		states.add(new Pair<String, String>("VT", "Vermont"));
		states.add(new Pair<String, String>("VA", "Virginia"));
		states.add(new Pair<String, String>("WA", "Washington"));
		states.add(new Pair<String, String>("WV", "West Virginia"));
		states.add(new Pair<String, String>("WI", "Wisconsin" ));
		states.add(new Pair<String, String>("WY", "Wyoming"));
	}
}
