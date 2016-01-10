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

import javax.inject.Inject;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/**
 * @author carl_000
 */
public class PersonsCellFactory implements Callback<TableColumn<Person, String>, TableCell<Person, String>> {

    private EventHandler<ActionEvent> deletePersonsHandler;

    @Inject
    PersonDAO dao;
    
    @Override
    public TableCell<Person, String> call(TableColumn<Person, String> param) {

        return new TextFieldTableCell<Person, String>(new DefaultStringConverter()) {      
        	
        	{
            	ContextMenu cm = new ContextMenu();
            	MenuItem deletePersonsMenuItem = new MenuItem("Delete");
            	deletePersonsMenuItem.setOnAction( PersonsCellFactory.this.deletePersonsHandler );
            	cm.getItems().add(deletePersonsMenuItem);
            	this.setContextMenu(cm);      
            	
        	}
        	
            @Override
            public void updateItem(String arg0, boolean empty) {
            	
                super.updateItem(arg0, empty);
                
                if( !empty ) {
                    this.setText( arg0 );
                } else {
                    this.setText( null );  // clear from recycled obj
                }
            }

			@SuppressWarnings("unchecked")
			@Override
			public void commitEdit(String newValue) {
				super.commitEdit(newValue);
				TableRow<Person> row = this.getTableRow();
				Person p = row.getItem();
				
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() {
						dao.updatePerson(p);  // updates AR too
						return null;
					}
				};
				new Thread(task).start();
			}
        };
    }
    
    public void setDeletePersonsHandler(EventHandler<ActionEvent> handler) {
    	this.deletePersonsHandler = handler;
    }
}
