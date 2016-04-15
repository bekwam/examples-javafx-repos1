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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.RandomStringUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * @author carl
 *
 */
public class BigTableController {

	private final static int NUM_RECORDS = 20_000_000;
	
	@FXML
	TableColumn<MyObject,Number> tcId;
	
	@FXML
	TableColumn<MyObject,String> tcData;
	
	@FXML
	TableView<MyObject> tblObjects;
	
	@FXML
	Button btnSave;
	
	private final BooleanProperty dirtyFlag = new SimpleBooleanProperty();
	
	private final Queue<UpdateObject> updateList = new LinkedList<>();
	
	static <T, S> T getObjectAtEvent(CellEditEvent<T, S> evt) {
		
		TableView<T> tableView = evt.getTableView();
		
		ObservableList<T> items = tableView.getItems();
		
		TablePosition<T,S> tablePosition = evt.getTablePosition();
		
		int rowId = tablePosition.getRow();
		
		T obj = items.get(rowId);

		return obj;
	}
	
	@FXML
	public void initialize() {
		
		btnSave.disableProperty().bind( dirtyFlag.not() );
		
		// id is read-only
		tcId.setCellValueFactory(new PropertyValueFactory<MyObject,Number>("id"));		
		
		tcData.setCellValueFactory(new PropertyValueFactory<MyObject,String>("data"));		
		tcData.setCellFactory(TextFieldTableCell.forTableColumn());				
		tcData.setOnEditCommit((evt) -> {
			MyObject obj = getObjectAtEvent(evt);
			if( !dirtyFlag.get() ) {
				dirtyFlag.set(true);
			}
			String oldData = obj.getData();
			obj.setData( evt.getNewValue() );
			updateList.add( new UpdateObject(obj.getId(), obj.getData(), oldData));
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
		// put in control
		//
		tblObjects.setItems( FXCollections.observableList( objectsFromDataSource ));
	}
	
	@FXML
	public void save() {

		UpdateObject uo = null;
		while( (uo = updateList.poll()) != null ) {
			System.out.println("updating " + uo);
		}

		dirtyFlag.set(false);
	}
}
