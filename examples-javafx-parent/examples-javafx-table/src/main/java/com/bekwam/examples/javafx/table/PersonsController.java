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
package com.bekwam.examples.javafx.table;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author carl_000
 */
public class PersonsController {

    @FXML
    TableView<Person> tblPersons;

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
    public void initialize() {

        tcFirstName.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName")
        );
        tcFirstName.setCellFactory(new PersonsCellFactory((evt) -> deletePersons()));

        tcLastName.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName")
        );
        tcLastName.setCellFactory(new PersonsCellFactory((evt) -> deletePersons()));

        tcEmail.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email")
        );
        tcEmail.setCellFactory(new PersonsCellFactory((evt) -> deletePersons()));

        tblPersons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void addPerson() {
        tblPersons.getItems().add(new Person());
    }

    @FXML
    public void deletePersons() {
        tblPersons.getItems().removeAll(tblPersons.getSelectionModel().getSelectedItems());
    }
}
