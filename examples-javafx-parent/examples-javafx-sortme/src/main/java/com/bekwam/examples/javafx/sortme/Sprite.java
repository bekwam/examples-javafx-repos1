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

import java.io.Serializable;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author carl
 *
 */
public class Sprite implements Serializable {
	
	private static final long serialVersionUID = 1351843411944357891L;
	
	final Pane spriteContainer;
	final Group normal;
	final Group highlight;
	final Group drag;
	final Group error;
	final StringProperty textProperty;
	
	/**
	 * Track the position of the mouse within a sprite when a drag is 
	 * started.  -1.0d means that a drag is not in effect.
	 */
	private double mouseInSpriteX = -1.0d;
	private double mouseInSpriteY = -1.0d;
	
	public Sprite(Pane spriteContainer, Group normal, Group highlight, Group drag, Group error, String text) {
		
		this.spriteContainer = spriteContainer;			
		this.normal = normal;
		this.highlight = highlight;
		this.drag = drag;		
		this.error = error;
		
		this.spriteContainer.addEventHandler(MouseEvent.ANY, mouseHandler);
		
		this.normal.setVisible(true);
		this.normal.setLayoutX( 0.0d );
		
		this.highlight.setVisible(false);
		this.highlight.setLayoutX( 0.0d );
		
		this.drag.setVisible(false);
		this.drag.setLayoutX( 0.0d );
		
		this.error.setVisible(false);
		this.error.setLayoutX( 0.0d );
		
		textProperty = new SimpleStringProperty( text );

		((Text)this.normal.getChildren().get(1)).textProperty().bind( textProperty );
		((Text)this.highlight.getChildren().get(1)).textProperty().bind( textProperty );
		((Text)this.drag.getChildren().get(1)).textProperty().bind( textProperty );
		((Text)this.error.getChildren().get(1)).textProperty().bind( textProperty );
	}
	
	public String getText() {
		return textProperty.get();
	}
	
	private Group copyGroup(Group sourceGroup) {
		
		Group copyGroup = new Group();
		copyGroup.getStyleClass().addAll( sourceGroup.getStyleClass() );

		Rectangle sourceBackground = (Rectangle)sourceGroup.getChildren().get(0);
		
		Rectangle copyBackground = new Rectangle();
		copyBackground.setWidth( sourceBackground.getWidth() );
		copyBackground.setHeight( sourceBackground.getHeight() );
		copyBackground.getStyleClass().addAll( sourceBackground.getStyleClass() );
		copyBackground.setEffect( sourceBackground.getEffect() );
		copyBackground.setOpacity( sourceBackground.getOpacity() );
		
		Text sourceText = (Text)sourceGroup.getChildren().get(1);
		
		Text copyText = new Text();
		copyText.getStyleClass().addAll( sourceText.getStyleClass() );
		copyText.setLayoutX( sourceText.getLayoutX() );
		copyText.setLayoutY( sourceText.getLayoutY() );
		copyText.setText( sourceText.getText() );
		copyText.setOpacity( sourceBackground.getOpacity() );
		
		copyGroup.getChildren().addAll( copyBackground, copyText );
		
		return copyGroup;
	}
	
	public Sprite create(String text) { // TODO: add in string param
		
		Pane copySpriteContainer = new Pane();
		copySpriteContainer.getStyleClass().addAll( spriteContainer.getStyleClass() );
		
		Group copyNormal = copyGroup( normal );
		Group copyHighlight = copyGroup( highlight );
		Group copyDrag = copyGroup( drag );
		Group copyError = copyGroup( error );
		
		copySpriteContainer.getChildren().addAll( copyNormal, copyHighlight, copyDrag, copyError );
		
		Sprite copySprite = new Sprite( copySpriteContainer, copyNormal, copyHighlight, copyDrag, copyError, text );
		
		return copySprite;
	}
	
	public void relocate(double x, double y) {		
		spriteContainer.relocate(x, y);
	}
	
	private EventHandler<MouseEvent> mouseHandler = (evt) -> {
		
		if( evt.getEventType() == MouseEvent.MOUSE_ENTERED && !evt.isPrimaryButtonDown()) {
			
			if( !this.highlight.isVisible() ) {
				this.normal.setVisible(false);
				this.highlight.setVisible(true);
				this.drag.setVisible(false);
				this.error.setVisible(false);
			}
			
		} else if( evt.getEventType() == MouseEvent.MOUSE_EXITED && !evt.isPrimaryButtonDown() ) {

			if( !this.normal.isVisible() ) {
				this.normal.setVisible(true);
				this.highlight.setVisible(false);
				this.drag.setVisible(false);
				this.error.setVisible(false);
			}
			
		} else if( evt.getEventType() == MouseEvent.MOUSE_DRAGGED ) {
			
			if( !this.drag.isVisible() ) {

				this.normal.setVisible( false );
				this.highlight.setVisible(false);
				this.drag.setVisible(true);
				this.error.setVisible(false);
			}

			if( mouseInSpriteX == -1.0d || mouseInSpriteY == -1.0d ) {
				
				Point2D spriteInParent = this.spriteContainer.localToParent( this.spriteContainer.getLayoutBounds().getMinX(), this.spriteContainer.getLayoutBounds().getMinY() );
				
				double spriteMinX = spriteInParent.getX();					
				double spriteMinY = spriteInParent.getY();
				
				mouseInSpriteX = evt.getSceneX() - spriteMinX;
				mouseInSpriteY = evt.getSceneY() - spriteMinY;
				
			} else {
			
				this.spriteContainer.relocate(
						evt.getSceneX() - mouseInSpriteX, 
						evt.getSceneY() - mouseInSpriteY
						);
			}
			
		} else if( evt.getEventType() == MouseEvent.MOUSE_RELEASED ) {
			
			if( mouseInSpriteX != -1.0d && mouseInSpriteY != -1.0d ) {
				
				mouseInSpriteX = -1.0d;
				mouseInSpriteY = -1.0d;
				
				this.normal.setVisible(true);
				this.highlight.setVisible(false);
				this.drag.setVisible(false);
				this.error.setVisible(false);

			}
		}
	};
	
	public void flagAsError() {
		
		this.normal.setVisible(true);  // hidden behind error
		this.highlight.setVisible(false);
		this.drag.setVisible(false);
		this.error.setVisible(true);
		
		FadeTransition ft = new FadeTransition(Duration.seconds(4), this.error);
	    ft.setFromValue(1.0);
	    ft.setToValue(0.0);
	    ft.setOnFinished( (evt) -> {
	    	
			this.normal.setVisible(true);  // show normal
			this.highlight.setVisible(false);
			this.drag.setVisible(false);
			this.error.setVisible(false);

	    	this.error.setOpacity( 1.0d );  // restore opacity
	    	
	    });
	    ft.play();
	}
}