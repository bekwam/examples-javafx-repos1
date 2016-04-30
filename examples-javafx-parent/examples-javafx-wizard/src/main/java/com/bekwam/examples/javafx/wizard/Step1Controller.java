package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Step1Controller {

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
}
