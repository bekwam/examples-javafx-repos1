package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step2Controller {

    private Logger log = LoggerFactory.getLogger(Step2Controller.class);

    @FXML
    TextField tfField4;

    @Inject
    WizardData model;

    @FXML
    public void initialize() {
        tfField4.textProperty().bindBidirectional(model.field4Property());
    }
}
