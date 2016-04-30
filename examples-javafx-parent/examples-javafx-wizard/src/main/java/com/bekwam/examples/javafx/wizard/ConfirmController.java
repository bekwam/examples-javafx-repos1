package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ConfirmController {

    @FXML
    TextField tfField1, tfField2, tfField3, tfField4, tfField5, tfField6, tfField7;

    @Inject
    WizardData model;

    @FXML
    public void initialize() {
        tfField1.textProperty().bind(model.field1Property());
        tfField2.textProperty().bind(model.field2Property());
        tfField3.textProperty().bind(model.field3Property());
        tfField4.textProperty().bind(model.field4Property());
        tfField5.textProperty().bind(model.field5Property());
        tfField6.textProperty().bind(model.field6Property());
        tfField7.textProperty().bind(model.field7Property());
    }
}
