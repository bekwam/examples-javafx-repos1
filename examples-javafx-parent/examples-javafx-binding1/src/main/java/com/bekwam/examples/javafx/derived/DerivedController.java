package com.bekwam.examples.javafx.derived;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by carl on 5/2/16.
 */
public class DerivedController {

    @FXML
    ChoiceBox<String> cbDriver;

    @FXML
    TextField tfDBHost, tfDBPort, tfDBSID, tfDBURL;

    @FXML
    public void initialize() {

        cbDriver.getItems().addAll( "thin", "oci");
        cbDriver.setValue("thin");

        // ex, jdbc:oracle:thin:@localhost:1521:orcl

        tfDBURL.textProperty().bind(
                Bindings.concat(
                   "jdbc:oracle:",
                        cbDriver.valueProperty(),
                        ":",
                        tfDBHost.textProperty(),
                        ":",
                        tfDBPort.textProperty(),
                        ":",
                        tfDBSID.textProperty()
                ));
    }
}
