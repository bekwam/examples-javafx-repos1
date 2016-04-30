package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Step2Controller {

    @FXML
    TextField tfField4;

    @Inject
    WizardData model;

    @FXML
    public void initialize() {
        tfField4.textProperty().bindBidirectional(model.field4Property());
    }
}
