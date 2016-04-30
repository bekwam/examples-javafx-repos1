package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Step3Controller {

    @FXML
    TextField tfField5, tfField6, tfField7;

    @Inject
    WizardData model;

    @FXML
    public void initialize() {
        tfField5.textProperty().bindBidirectional(model.field5Property());
        tfField6.textProperty().bindBidirectional(model.field6Property());
        tfField7.textProperty().bindBidirectional(model.field7Property());
    }
}


