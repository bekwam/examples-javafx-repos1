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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

/**
 * JavaFX Controller for main view
 * 
 * @author carlwalker
 *
 */
public class ListViewHelperMainController {

	@FXML
	TextField tfEmployee;
	
	@FXML
	public void clear() { tfEmployee.setText( "" ); }

	@FXML
	public void showEmployeesHelper(ActionEvent evt) {
		
		Button btn = (Button)evt.getSource();
		
		Point2D point = btn.localToScreen(0.0d + btn.getWidth(), 0.0d - btn.getHeight());
		
		try {
			
			Popup employeesHelper = new ListViewHelperEmployeesPopup(tfEmployee, point);
			
			employeesHelper.show(btn.getScene().getWindow());
		
		} catch(Exception exc) {
			exc.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR, "Error creating employees popup; exiting");
			alert.showAndWait();
			btn.getScene().getWindow().hide();  // close and implicit exit
		}
	}
}
