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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/**
 * @author carl_000
 */
public class PersonTypeCellFactory implements Callback<TableColumn<Person, String>, TableCell<Person, String>> {

	private Logger logger = Logger.getLogger("com.bekwam.examples.javafx.table");

	private EventHandler<ActionEvent> deletePersonsHandler;

    @Inject
    PersonDAO dao;
    
    @Override
    public TableCell<Person, String> call(TableColumn<Person, String> param) {

        return new ComboBoxTableCell<Person, String>(new DefaultStringConverter()) {      
        	
        	{
            	ContextMenu cm = new ContextMenu();
            	MenuItem deletePersonsMenuItem = new MenuItem("Delete");
            	deletePersonsMenuItem.setOnAction( PersonTypeCellFactory.this.deletePersonsHandler );
            	cm.getItems().add(deletePersonsMenuItem);
            	this.setContextMenu(cm);    
            	
            	this.getItems().addAll( "Friend", "Co-worker", "Other" );
            	
            	this.setEditable(true);
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
				
				String msg = p.validate();
				if( logger.isLoggable(Level.FINE) ) {
					logger.fine("[COMMIT EDIT] validate=" + msg);
				}
					
				if( msg == null || msg.isEmpty() ) {
					if( logger.isLoggable(Level.FINE) ) {
						logger.fine("[COMMIT EDIT] validation passed");
					}
					Task<Void> task = new Task<Void>() {
						@Override
						protected Void call() {
							dao.updatePerson(p);  // updates AR too
							return null;
						}
					};
					new Thread(task).start();
				} else {
					
					System.out.println("layoutBounds=" + this.getLayoutBounds());
					System.out.println("boundsInLocal=" + this.getBoundsInLocal());
					System.out.println("boundsInParent=" + this.getBoundsInParent());
					System.out.println("boundsInParent (Scene)=" + this.localToScene(this.getBoundsInParent()));
					System.out.println("boundsInParent (Screen)=" + this.localToScreen(this.getBoundsInParent()));

					System.out.println("row layoutBounds=" + row.getLayoutBounds());
					System.out.println("row boundsInLocal=" + row.getBoundsInLocal());
					System.out.println("row boundsInParent=" + row.getBoundsInParent());
					System.out.println("row boundsInParent (Scene)=" + row.localToScene(this.getBoundsInParent()));
					System.out.println("row boundsInParent (Screen)=" + row.localToScreen(this.getBoundsInParent()));

					VBox vbox = new VBox();
					vbox.setPadding(new Insets(10.0d));
					vbox.setStyle("-fx-background-color: white; -fx-border-color: red");
					vbox.getChildren().add( new Label(msg));
					//vbox.setEffect(new DropShadow());
					
					Popup popup = new Popup();
					popup.getContent().add( vbox );
					popup.setAutoHide(true);
					popup.setOnShown((evt) -> {
						
						System.out.println("vbox layoutBounds=" + vbox.getLayoutBounds());
						System.out.println("vbox boundsInLocal=" + vbox.getBoundsInLocal());
						System.out.println("vbox boundsInParent=" + vbox.getBoundsInParent());
						System.out.println("vbox boundsInParent (Scene)=" + vbox.localToScene(this.getBoundsInParent()));
						System.out.println("vbox boundsInParent (Screen)=" + vbox.localToScreen(this.getBoundsInParent()));

					});
					popup.show( row, 
							row.localToScreen(row.getBoundsInParent()).getMinX(), 
							row.localToScreen(row.getBoundsInParent()).getMaxY());					
				}
			}
        };
    }
    
    public void setDeletePersonsHandler(EventHandler<ActionEvent> handler) {
    	this.deletePersonsHandler = handler;
    }
}
