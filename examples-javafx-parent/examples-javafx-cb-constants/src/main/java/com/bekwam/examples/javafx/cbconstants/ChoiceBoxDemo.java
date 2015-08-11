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
package com.bekwam.examples.javafx.cbconstants;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * A JavaFX Application demonstrating a technique for applying a standard
 * yes/no list throughout the app for ChoiceBox and similar controls
 * 
 * @author carlwalker
 *
 */
public class ChoiceBoxDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		
		Label label = new Label("Make Yes/No Selection");
		
		ChoiceBox<Pair<String, String>> cb = new ChoiceBox<>();
		cb.setItems( Constants.LIST_YES_NO );
		cb.setConverter( new PairStringConverter() );
		cb.setValue( Constants.PAIR_NO );
		
		Label labelOpt = new Label("Make Yes/No Selection (Optional)");

		ChoiceBox<Pair<String, String>> cbOpt = new ChoiceBox<>();
		cbOpt.setItems( Constants.LIST_YES_NO_OPT );
		cbOpt.setConverter(new PairStringConverter(true) );
		cbOpt.setValue( Constants.PAIR_NULL );
		
		Button b = new Button("Save");
		b.setOnAction( (evt) -> 
			System.out.println("Selections - yes/no was '" + cb.getValue() + "' and yes/no/opt was '" + cbOpt.getValue() + "'")
		);
		
		vbox.getChildren().addAll(label, cb, labelOpt, cbOpt, b);
		
		Scene scene = new Scene(vbox);
		
		primaryStage.setTitle("Choice Box Demo");
		primaryStage.setHeight(200);
		primaryStage.setWidth(300);
		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
