package com.bekwam.examples.javafx.tvtable;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

/**
 * Custom view of TVProgram for ListView control
 *
 * @author carl
 */
public class TVProgramListCell extends ListCell<TVProgram> {

    private Label programNameLabel = new Label("");
    private Label lastRecordingLabel = new Label("");

    public TVProgramListCell() {

        programNameLabel.getStyleClass().add( "tvtable-program-name" );
        lastRecordingLabel.getStyleClass().add( "tvtable-last-recording" );

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        VBox vbox = new VBox();
        vbox.getStyleClass().add( "tvtable-cell-container" );
        vbox.getChildren().addAll( programNameLabel );
        setGraphic( vbox );

        vbox.hoverProperty().addListener((obs, ov, onHover) -> {
            // turn a hover event into a forced focus call
            if( onHover ) {
//                this.getListView().getFocusModel().focus( this.getIndex() );
                this.getListView().getSelectionModel().select( this.getIndex() );
            }
        });

//        this.focusedProperty().addListener((obs,ov,nv) -> {
        this.selectedProperty().addListener((obs,ov,nv) -> {

            if( nv ) {
                if( !vbox.getChildren().contains(lastRecordingLabel) ) {
                    vbox.getChildren().add( lastRecordingLabel );
                }

            } else {
                if( vbox.getChildren().contains(lastRecordingLabel) ) {
                    vbox.getChildren().remove(lastRecordingLabel);
                }
            }
        });
    }

    @Override
    public void updateItem(TVProgram item, boolean empty) {

        super.updateItem(item,empty);

        if( item != null && !empty ) {

            if( this.getStyle().contains("tvtable-list-cell-empty") ) {
                this.getStyleClass().remove("tvtable-list-cell-empty");
            }

            if( !this.getStyleClass().contains("tvtable-list-cell" ) ) {
                this.getStyleClass().add( "tvtable-list-cell" );
            }

            programNameLabel.setText( formatProgramName(item) );
            lastRecordingLabel.setText( formatLastRecording(item) );
        } else {

            if( !this.getStyle().contains("tvtable-list-cell-empty") ) {
                this.getStyleClass().add("tvtable-list-cell-empty");
            }

            if( this.getStyleClass().contains("tvtable-list-cell" ) ) {
                this.getStyleClass().remove( "tvtable-list-cell" );
            }

        }
    }

    private String formatProgramName(TVProgram item ) {
        String format = "%s (%d)";
        return String.format(format, item.getProgramName(), item.getEpisodesRecorded());
    }

    private String formatLastRecording(TVProgram item) {
        return "Recordings added " + item.getLastRecording().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
}
