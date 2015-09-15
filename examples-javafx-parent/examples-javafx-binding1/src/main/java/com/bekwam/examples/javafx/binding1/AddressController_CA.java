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

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * The Canadian address form for the second demo
 * 
 * @see Binding2App
 * @see AddressController2
 * 
 * @author carlwalker
 *
 */
public class AddressController_CA extends VBox {

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
	TextField tfPostalCode;
	
	private final List<Pair<String, String>> provinces = new ArrayList<>();

	public AddressController_CA() {
		initCanadianProvinces();
	}
	
	@FXML
	public void initialize() {				
		cbCAProvince.setItems( FXCollections.observableArrayList(provinces) );
		cbCAProvince.setConverter(new PairStringConverter(provinces));
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
	
	public void clearForm() {
		tfAddress1.setText("");
		tfAddress2.setText("");
		tfCity.setText("");
		cbCAProvince.getSelectionModel().clearSelection();
		tfPostalCode.setText("");
	}
}
