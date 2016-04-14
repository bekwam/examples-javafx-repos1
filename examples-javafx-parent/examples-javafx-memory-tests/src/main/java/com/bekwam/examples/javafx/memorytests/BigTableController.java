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
package com.bekwam.examples.javafx.memorytests;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author carl
 *
 */
public class BigTableController {

	private final static int NUM_RECORDS = 20_000_000;
	
	@FXML
	TableColumn<MyFXObject,Number> tcId;
	
	@FXML
	TableColumn<MyFXObject,String> tcData;
	
	@FXML
	TableView<MyFXObject> tblObjects;
	
	@FXML
	public void initialize(){
		
/*		tcId.setCellValueFactory(new PropertyValueFactory<MyFXObject,Number>("id"));
		
		tcData.setCellValueFactory(new PropertyValueFactory<MyFXObject,String>("data"));
*/
		tcId.setCellValueFactory(new PropertyValueFactory<MyFXObject,Number>("id") {

			@Override
			public ObservableValue<Number> call(CellDataFeatures<MyFXObject, Number> param) {
				return param.getValue().idProperty();
			}
			
		});
		
		tcData.setCellValueFactory(new PropertyValueFactory<MyFXObject,String>("data") {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MyFXObject, String> param) {
				return param.getValue().dataProperty();
			}
			
		});
	}
	
	private List<MyObject> fetchData() {
		
		List<MyObject> objectsFromDataSource = new ArrayList<>();
		
		for( int i=0; i<NUM_RECORDS; i++ ) {
			objectsFromDataSource.add( new MyObject( (long)i, RandomStringUtils.randomAlphabetic(20) ) );
		}
		
		return objectsFromDataSource;
	}
	
	@FXML
	public void loadTable() {
		
		//
		// load the data into domain objects
		//
		List<MyObject> objectsFromDataSource = fetchData();
		
		//
		// transfer to an FX-ready object
		//	
		List<MyFXObject> fxObjects = new ArrayList<>();
		for( MyObject obj : objectsFromDataSource ) {
			fxObjects.add( new MyFXObject(obj.getId(), obj.getData()) );
		}
		
		//
		// put in control
		//
		tblObjects.setItems( FXCollections.observableList( fxObjects ));
	}
}
