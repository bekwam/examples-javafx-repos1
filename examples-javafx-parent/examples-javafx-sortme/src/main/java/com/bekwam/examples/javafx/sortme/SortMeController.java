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

import javafx.fxml.FXML;
import javafx.scene.Group;
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
	public void initialize() {
	
		Sprite sprite = new Sprite( spriteContainer, normal, highlight, drag );
		
		Sprite sprite2 = sprite.create();
		sprite2.relocate( 0, 50.0d );
		
		Sprite sprite3 = sprite.create();
		sprite3.relocate( 0, 100.0d );
		
		Sprite sprite4 = sprite.create();
		sprite4.relocate( 0, 150.0d );

		background.getChildren().addAll( sprite2.spriteContainer, sprite3.spriteContainer, sprite4.spriteContainer );
	}
	
	@FXML
	public void check() { System.out.println("on action");}
	
	
/*    private DropShadow pieceTextHighlight = new DropShadow(3.0d, Color.web("#004489"));
    private InnerShadow pieceBackgroundHighlight = new InnerShadow(10.0d, Color.web("#004489"));
    
	@FXML
	public void initialize() {		
		
		sourcePane.getChildren().remove( templatePiece );
		
		int startY = 10;
		int currentY = 0;
		for( int i=0; i<5; i++ ) {
			Group p = createPiece(String.valueOf(i));
			p.relocate(10, startY + currentY );
			currentY += 60;
			sourcePane.getChildren().add( p );
		}	
	}
	
	private Group createPiece(String text_s) {
		
		Group g = new Group();
		g.getStyleClass().addAll( templatePiece.getStyleClass() );
		
		Rectangle templateBackground = (Rectangle)templatePiece.getChildren().get(0);
		
		Rectangle background = new Rectangle();
		background.setWidth( templateBackground.getWidth() );
		background.setHeight( templateBackground.getHeight() );
		background.getStyleClass().addAll( templateBackground.getStyleClass() );
		background.setEffect( templateBackground.getEffect() );
		
		Text templateText = (Text)templatePiece.getChildren().get(1);
		
		Text text = new Text();
		text.getStyleClass().addAll( templateText.getStyleClass() );
		text.setLayoutX( templateText.getLayoutX() );
		text.setLayoutY( templateText.getLayoutY() );
		text.setText( text_s );
		
		g.getChildren().addAll( background, text );
		
		g.setOnMouseEntered( (evt) -> {
			text.setEffect(pieceTextHighlight);
			background.setEffect(pieceBackgroundHighlight);
		});
		
		g.setOnMouseExited( (evt) -> {
			text.setEffect(null);	
			background.setEffect(null);
		});
		
		sourcePane.setOnMousePressed( (evt) -> System.out.println("mouse clicked") );
		
		return g;
	}*/
}
