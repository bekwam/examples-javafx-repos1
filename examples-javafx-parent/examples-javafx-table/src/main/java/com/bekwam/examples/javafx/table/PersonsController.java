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
package com.bekwam.examples.javafx.table;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * @author carl_000
 */
public class PersonsController {

	private Logger logger = Logger.getLogger("com.bekwam.examples.javafx.table");

	@FXML
    TableView<Person> tblPersons;

	@FXML
	TableColumn<Person, String> tcType;
	
    @FXML
    TableColumn<Person, String> tcFirstName;

    @FXML
    TableColumn<Person, String> tcLastName;

    @FXML
    TableColumn<Person, String> tcEmail;

    @FXML
    ProgressBar pbStatus;

    @FXML
    Label lblStatus;

    @FXML
    HBox hboxStatus;
    
    @FXML
    TextField tfFilter;
    
    @FXML
    Button btnAdd;
    
    @Inject
    PersonDAO dao;
    
    @Inject
    Provider<PersonsCellFactory> personsCellFactoryProvider;
    
    @Inject
    Provider<PersonTypeCellFactory> personTypeCellFactoryProvider;
    
    private final ObservableList<Person> personsActiveRecord;
    
    @Inject
    public PersonsController(ObservableList<Person> personsActiveRecord) {
    	this.personsActiveRecord = personsActiveRecord;
    }
    
    @FXML
    public void initialize() {

    	PersonTypeCellFactory ptcf = personTypeCellFactoryProvider.get();
    	ptcf.setDeletePersonsHandler((evt) -> deletePersons());
    	tcType.setCellValueFactory(
    			new PropertyValueFactory<Person, String>("personType")
    	);
    	tcType.setCellFactory(ptcf);
    	
    	PersonsCellFactory pcf1 = personsCellFactoryProvider.get();
    	pcf1.setDeletePersonsHandler((evt) -> deletePersons());
        tcFirstName.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName")
        );
        tcFirstName.setCellFactory(pcf1);

    	PersonsCellFactory pcf2 = personsCellFactoryProvider.get();
    	pcf2.setDeletePersonsHandler((evt) -> deletePersons());
        tcLastName.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName")
        );
        tcLastName.setCellFactory(pcf2);

    	PersonsCellFactory pcf3 = personsCellFactoryProvider.get();
    	pcf3.setDeletePersonsHandler((evt) -> deletePersons());
        tcEmail.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email")
        );
        tcEmail.setCellFactory(pcf3);

        tblPersons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                
        hboxStatus.setVisible( false );
        
        tblPersons.setItems( personsActiveRecord );
        
        btnAdd.disableProperty().bind( tfFilter.textProperty().isNotEmpty());
    }

    @FXML
    public void addPerson() {
    	    	
    	final Person p = new Person();
    	
//        tblPersons.getItems().add(p); 

        personsActiveRecord.add(p);
        
        Task<Long> task = new Task<Long>() {
			@Override
			protected Long call() throws Exception {

				updateProgress(0.1d, 1.0d);
				updateMessage( "Saving person");
				Long personId = dao.addPerson(p);

				return personId;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				updateProgress(1.0d, 1.0d);
				updateMessage("Save completed");
		        p.setPersonId(getValue());  // sets obj in UI and in AR
			}    		
    	};
    	
    	hboxStatus.visibleProperty().bind( task.runningProperty() );
    	pbStatus.progressProperty().bind( task.progressProperty() );
    	lblStatus.textProperty().bind( task.messageProperty() );
    	
    	new Thread(task).start();
    }

    @FXML
    public void deletePersons() {

    	List<Long> personIds = 
    			tblPersons
    				.getSelectionModel()
    				.getSelectedItems()
    				.stream()
    				.map(Person::getPersonId)
    				.collect(Collectors.toList());
    	
        personsActiveRecord.removeAll(tblPersons.getSelectionModel().getSelectedItems());
        
        // this has to be called last b/c selection will be removed too
        //tblPersons.getItems().removeAll(tblPersons.getSelectionModel().getSelectedItems());

        Task<Void> task = new Task<Void>() {
        	@Override
        	protected Void call() throws Exception {
            	dao.deletePersons(personIds);      
            	return null;
        	}
        };
        
    	hboxStatus.visibleProperty().bind( task.runningProperty() );
    	pbStatus.progressProperty().bind( task.progressProperty() );
    	lblStatus.textProperty().bind( task.messageProperty() );

    	new Thread(task).start();
    }
    
    @FXML
    public void clearFilter() { 
    	tfFilter.textProperty().set("");
    	doFilterTable( tfFilter );
    }
    
    protected void doFilterTable(TextField tf) {
    	String criteria = tf.getText();
    	
    	if( logger.isLoggable(Level.FINE) ) {
    		logger.fine( "[FILTER] filtering on=" + criteria );
    	}
    	
    	if( criteria == null || criteria.isEmpty() ) {
    		tblPersons.setItems( personsActiveRecord );
    		return;
    	}
    	
    	FilteredList<Person> fl = new FilteredList<>(personsActiveRecord, p -> true);
    	
    	fl.setPredicate(person -> {

    		if (criteria == null || criteria.isEmpty()) {
                 return true;
             }

             String lcCriteria = criteria.toLowerCase();

             if (person.getFirstName().toLowerCase().contains(lcCriteria)) {
                 return true; // Filter matches first name.
             } else if (person.getLastName().toLowerCase().contains(lcCriteria)) {
                 return true; // Filter matches last name.
             } else if (person.getEmail().toLowerCase().contains(lcCriteria)) {
            	 return true;  // 	matches email
             }
             
             return false; // Does not match.
         });
    	
    	 SortedList<Person> sortedData = new SortedList<>(fl);
    	 sortedData.comparatorProperty().bind(tblPersons.comparatorProperty());  // ?    	
    	 tblPersons.setItems(sortedData);
    }
    
    @FXML
    public void filterTable(ActionEvent evt) {

    	doFilterTable( (TextField)evt.getSource() );
    }    
    
    @FXML
    public void refresh() {
    	
    	Task<List<Person>> task = new Task<List<Person>>() {
    		@Override
    		public List<Person> call() throws Exception {

    			List<Person> persons = dao.findAll();

    			return persons;
    		}

			@Override
			protected void succeeded() {
				super.succeeded();
				tblPersons.getItems().clear();
				tblPersons.getItems().addAll( getValue() );
			}
    	};
    	
    	hboxStatus.visibleProperty().bind( task.runningProperty() );
    	pbStatus.progressProperty().bind( task.progressProperty() );
    	lblStatus.textProperty().bind( task.messageProperty() );

    	new Thread(task).start();
    }
}
