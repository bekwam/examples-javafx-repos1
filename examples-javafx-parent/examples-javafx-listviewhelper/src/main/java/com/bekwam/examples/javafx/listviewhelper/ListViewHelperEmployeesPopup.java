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
package com.bekwam.examples.javafx.listviewhelper;

import java.lang.ref.WeakReference;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.util.Duration;

/**
 * Popup subclass for Employees Popup
 * 
 * @author carlwalker
 *
 */
public class ListViewHelperEmployeesPopup extends Popup {

	private WeakReference<TextField> tfTargetRef;
	
	public ListViewHelperEmployeesPopup(TextField tfTarget, Point2D position) throws Exception{
		
		tfTargetRef = new WeakReference<>(tfTarget);
		
		FXMLLoader fxmlLoader = new FXMLLoader(ListViewHelperApp.class.getResource("/ListViewHelperEmployees.fxml"));
		
		Parent content = fxmlLoader.load();
		
		ListViewHelperEmployeesController controller = fxmlLoader.getController();
		
		controller
			.lvEmployees
				.getSelectionModel()
				.selectedItemProperty()
				.addListener((obs, oldValue, newValue) -> {
					
			if( newValue != null ) {
				
				tfTargetRef.get().setText( newValue );

				// no delay -- and no feedback
				//ListViewHelperEmployeesPopup.this.hide();
				
				// slight delay + gentle fade -- maintains highlight
				Duration duration = new Duration( 300 );
				
				KeyValue kvOpacity = new KeyValue(content.opacityProperty(), 0);
		        
				KeyFrame atEndFrame = new KeyFrame( 
						duration, 
						(evt) -> ListViewHelperEmployeesPopup.this.hide(),
						kvOpacity
						);
				
				Timeline timeline = new Timeline();
				timeline.getKeyFrames().add( atEndFrame );
				timeline.play();
			}
			
		});
		
		setX( position.getX() );
		setY( position.getY() );
		setAutoHide(true);		
		getContent().add( content );
	}
}
