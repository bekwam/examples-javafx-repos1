package com.bekwam.examples.javafx.tvtable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;

import java.time.LocalDate;
import java.time.Month;

/**
 * JavaFX Controller for TVTable demo
 *
 * @author carl
 */
public class TVTableController {

    @FXML
    ListView<TVProgram> lvPrograms;

    @FXML
    Label lblStatus;

    @FXML
    public void initialize() {

        lblStatus.setText("");

        lvPrograms.setCellFactory( (lv) -> new TVProgramListCell() );

        lvPrograms.setOnMouseClicked( (evt) -> {
            TVProgram pgm = lvPrograms.getSelectionModel().getSelectedItem();
            if( pgm != null ) {
                lblStatus.setText("Starting " + pgm.getProgramName() + "..." );
            }
        });

        lvPrograms.setOnKeyPressed( (evt) -> {
            if( evt.getCode().equals( KeyCode.ENTER ) ) {
                TVProgram pgm = lvPrograms.getSelectionModel().getSelectedItem();
                if( pgm != null ) {
                    lblStatus.setText("Starting " + pgm.getProgramName() + "...");
                }
            }
        });
    }

    public void load() {

        //
        // Sample data
        //

        TVProgram[] programs = {
                new TVProgram("As the TableView Turns", 4, LocalDate.of(2016, Month.JULY, 2)),
                new TVProgram("The Walking Stage", 1, LocalDate.of(2016, Month.JUNE, 28)),
                new TVProgram("The Real Nodes of JavaFX", 12, LocalDate.of(2016, Month.JUNE, 20)),
                new TVProgram("Big Spinner After Dark", 1, LocalDate.of(2016, Month.JUNE, 15))
        };

        lvPrograms.getItems().addAll( programs );
    }
}
