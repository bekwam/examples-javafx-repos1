/*
 * Copyright 2015 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekwam.examples.javafx.waitnotify;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * @author carl_000
 */
public class GetDataDialogSynced {

    private VBox vbox;

    private String data = "";

    public void init() {
        vbox = new VBox();
        vbox.setSpacing(20.0d);
        vbox.setPadding(new Insets(10.0d));

        TextField tf = new TextField();

        Button btn = new Button("Submit");
        btn.setOnAction( (evt) -> {
            data = tf.getText();
            ((Button)evt.getSource()).getScene().getWindow().hide();
            synchronized(GetDataDialogSynced.this) {
                GetDataDialogSynced.this.notify();
            }
        } );

        vbox.getChildren().add(new Label("Enter Data"));
        vbox.getChildren().add(tf);
        vbox.getChildren().add(btn);
    }

    public VBox getRoot() { return vbox; }
    public String getData() { return data; }
}
