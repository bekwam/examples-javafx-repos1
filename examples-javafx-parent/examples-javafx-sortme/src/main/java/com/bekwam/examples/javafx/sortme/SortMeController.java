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
package com.bekwam.examples.javafx.sortme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

/**
 * @author carl
 *
 */
public class SortMeController {

	@FXML
	Pane background;
	
	@FXML
	Pane spriteContainer;
	
	@FXML
	Group normal;
	
	@FXML
	Group highlight;
	
	@FXML
	Group drag;

	@FXML
	Group error;
	
	private SpriteXComparator spriteYComparator = new SpriteXComparator();	
	private final List<Sprite> sprites = new ArrayList<>();	
	private final List<String> expectedResults = Arrays.asList( new String[]{"A", "B", "C", "D"} );
	
	@FXML
	public void initialize() {
	
		Sprite templateSprite = new Sprite( spriteContainer, normal, highlight, drag, error, "A" );
		
		background.getChildren().remove(spriteContainer);
		
		Sprite sprite = templateSprite.create("A");

		Sprite sprite2 = templateSprite.create("B");
		
		Sprite sprite3 = templateSprite.create("C");
		
		Sprite sprite4 = templateSprite.create("D");

		background.getChildren().addAll( sprite.spriteContainer, sprite2.spriteContainer, sprite3.spriteContainer, sprite4.spriteContainer );

		sprites.add( sprite );
		sprites.add( sprite2 );
		sprites.add( sprite3 );
		sprites.add( sprite4 );
		
		shuffle();
	}
	
	@FXML
	public void check() { 

		Collections.sort( sprites, spriteYComparator );
		
		boolean allMatch = true;
		
		System.out.println("checking...");
		
		List<Sprite> spritesToFlag = new ArrayList<>();
		for( int i=0; i<sprites.size(); i++ ) {
			System.out.println( "actual=" + sprites.get(i).getText() + ", expected=" + expectedResults.get(i) );
			if( !sprites.get(i).getText().equals( expectedResults.get(i)) ) {
				spritesToFlag.add( sprites.get(i) );
				allMatch = false;
			}
		}
		
		if( allMatch ) {
			
			ButtonType btnExit = new ButtonType("No, Exit");
			
			Alert alert = new Alert(
					AlertType.CONFIRMATION, 
					"Correct! Good Job!\nPlay Again?",
					ButtonType.YES,
					btnExit);
			
			alert.showAndWait().ifPresent(response -> {
			     if (response == ButtonType.YES) {
			         shuffle();
			     } else {
			    	 Platform.exit();
			     }
			 });	
		
		} else {
			
			Alert alert = new Alert(
					AlertType.WARNING, 
					"Incorrect. Try again?"
					);
			
			alert.showAndWait();
			
			spritesToFlag
				.stream()
				.forEach((sp)->sp.flagAsError());
			
		}
	}

	class SpriteXComparator implements Comparator<Sprite> {
		@Override
		public int compare(Sprite o1, Sprite o2) {
			return Double.compare( o1.spriteContainer.getLayoutY(), o2.spriteContainer.getLayoutY() );
		}		
	}
	
	@FXML
	public void shuffle() {
		
		Bounds bounds = background.getLayoutBounds();
		
		double minx = bounds.getMinX();
		double miny = bounds.getMinY();

		Random random = new Random();
		
		for( Sprite sp : sprites ) {			
			double maxx = bounds.getMaxX() - sp.spriteContainer.getWidth();
			double maxy = bounds.getMaxY() - sp.spriteContainer.getHeight();
			double x = random.nextDouble() * (maxx - minx);
			double y = random.nextDouble() * (maxy - miny);
			sp.relocate(x, y);
		}
		
	}
}
