package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step1Controller {

    private Logger log = LoggerFactory.getLogger(Step1Controller.class);

    @FXML
    TextField tfField1, tfField2, tfField3;

    @Inject
    WizardData model;

    @FXML
    public void initialize() {

        tfField1.textProperty().bindBidirectional( model.field1Property() );
        tfField2.textProperty().bindBidirectional( model.field2Property() );
        tfField3.textProperty().bindBidirectional( model.field3Property() );

    }

    @Validate
    public boolean validate() throws Exception {

        if( tfField1.getText() == null || tfField1.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 1");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 1 is required." );
            alert.showAndWait();
            return false;
        }

        if( tfField2.getText() == null || tfField2.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 1");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 2 is required." );
            alert.showAndWait();
            return false;
        }

        if( tfField3.getText() == null || tfField3.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 3");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 3 is required." );
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @Submit
    public void submit() throws Exception {

        if( log.isDebugEnabled() ) {
            log.debug("[SUBMIT] the user has completed step 1");
        }
    }
}
