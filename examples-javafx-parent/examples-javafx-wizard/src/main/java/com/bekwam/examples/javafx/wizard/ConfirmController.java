package com.bekwam.examples.javafx.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmController {

    private Logger log = LoggerFactory.getLogger(ConfirmController.class);

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

    @Submit
    public void submit() throws Exception {

        if( log.isDebugEnabled() ) {
            log.debug("[SUBMIT] saving fields 1-7 to the database via a web service call (not really)");
        }
    }

}
