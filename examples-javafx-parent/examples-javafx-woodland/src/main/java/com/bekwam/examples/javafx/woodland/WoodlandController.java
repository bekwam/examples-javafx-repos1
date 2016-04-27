package com.bekwam.examples.javafx.woodland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class WoodlandController {

	@FXML
	VBox boardContainer;
	
	@FXML
	Pane board;
	
	@FXML
	Pane boardPaneRow0;

	@FXML
	Pane boardPaneRow1;

	@FXML
	Pane boardPaneRow2;
	
	@FXML 
	Rectangle sq0_0;

	@FXML 
	Rectangle sq0_1;

	@FXML 
	Rectangle sq0_2;

	@FXML 
	Rectangle sq1_0;

	@FXML 
	Rectangle sq1_1;

	@FXML 
	Rectangle sq1_2;

	@FXML 
	Rectangle sq2_0;

	@FXML
	Rectangle sq2_1;

	@FXML 
	Rectangle sq2_2;
	
	@FXML
	ImageView gamePiece;

	/**
	 * Convenience for working with squares all at once
	 * 
	 */
	private final List<Rectangle> squares = new ArrayList<>();
	
	private final DropShadow dropShadow = new DropShadow();
	
	private Optional<Rectangle> lastHoverSquare = Optional.empty();
	
	private boolean gpSelected = false;
	
	@FXML
	public void initialize() {
		
		System.out.println("boardPaneRow0(" + boardPaneRow0.getLayoutX() + ", " + boardPaneRow0.getLayoutY() + ")");
		System.out.println("boardPaneRow1(" + boardPaneRow1.getLayoutX() + ", " + boardPaneRow1.getLayoutY() + ")");
		System.out.println("boardPaneRow2(" + boardPaneRow2.getLayoutX() + ", " + boardPaneRow2.getLayoutY() + ")");
		
		squares.addAll( Arrays.asList(sq0_0, sq0_1, sq0_2, sq1_0, sq1_1, sq1_2, sq2_0, sq2_1, sq2_2) );
		
		squares
			.stream()
			.forEach((sq) -> 
				System.out.println(sq.getId() + "(" + sq.getLayoutX() + ", " + sq.getLayoutY() + ")")
					);
			
		board.setOnMousePressed((evt) -> {			
			if( selectGamePiece(evt.getSceneX(), evt.getSceneY()) ) {
				gamePiece.setOpacity( 0.4d );
				gpSelected = true;
			}
		});
		
		board.setOnMouseReleased((evt) -> {			
			
			if( gpSelected ) {  // TODO: in board check
				
				Optional<Rectangle> onSquare = pickSquare( evt.getSceneX(), evt.getSceneY() );

				System.out.println("scene ending pt=" + evt.getSceneX() + ", " + evt.getSceneY());
				
				if( onSquare.isPresent() ) {					
					
					System.out.println( "sq=" + onSquare.get().getId());
					Point2D onSquarePt = new Point2D(onSquare.get().getLayoutX(), onSquare.get().getLayoutY());
					System.out.println( "onSquarePt=" + onSquarePt );
					Point2D onSquareParentPt = onSquare.get().localToParent(onSquarePt);
					System.out.println( "onSquareParentPt=" + onSquareParentPt );					
					Point2D onSquareScenePt = onSquare.get().localToScene( onSquarePt );
					System.out.println( "onSquarePt (scene)=" + onSquareScenePt);
					Point2D boardPt = board.sceneToLocal( onSquareScenePt );
					System.out.println( "boardPt=" + boardPt);
					gamePiece.relocate( boardPt.getX()+10, boardPt.getY()+10 );
				}
			}
			
			clearSelection();  // unconditionally clear in case missed event
		});

		board.setOnMouseDragged( (evt) -> {
			
			if( !inBoard( evt.getSceneX(), evt.getSceneY() ) ) {
				clearSelection();
			}
			
			if( gpSelected ) {				
				Point2D sceneEvtPt = new Point2D(evt.getSceneX(), evt.getSceneY());
				Point2D parentEvtPt = board.sceneToLocal(sceneEvtPt);
				Insets padding = boardContainer.getPadding();				
				gamePiece.relocate( parentEvtPt.getX()-padding.getLeft(), parentEvtPt.getY()-padding.getTop());					
			}
			
			Optional<Rectangle> hoverSquare = pickSquare( evt.getSceneX(), evt.getSceneY() );

			if( lastHoverSquare.isPresent() ) {
				lastHoverSquare.get().setEffect( null );
			}
			
			if( hoverSquare.isPresent() ) {
				hoverSquare.get().setEffect(dropShadow);
				lastHoverSquare = hoverSquare;
			}
		});
		
		board.setOnMouseMoved((evt) -> {

			if( !inBoard( evt.getSceneX(), evt.getSceneY() ) ) {
				clearSelection();
			}
			
			Optional<Rectangle> hoverSquare = pickSquare( evt.getSceneX(), evt.getSceneY() );

			if( lastHoverSquare.isPresent() ) {
				lastHoverSquare.get().setEffect( null );
			}
			
			if( hoverSquare.isPresent() ) {
				hoverSquare.get().setEffect(dropShadow);
				lastHoverSquare = hoverSquare;
			}
			
		});
	}

	private void clearSelection() {
		gamePiece.setOpacity(1.0d);
		gpSelected = false;
	}

	private boolean selectGamePiece(double sceneX, double sceneY) {	
		return isPicked( gamePiece, sceneX, sceneY );
	}
	
	private Optional<Rectangle> pickSquare(double sceneX, double sceneY) {
		
		Optional<Rectangle> sq =squares
			.stream()
			.filter((square) -> isPicked(square, sceneX, sceneY))
			.findFirst();
		
		return sq;
	}
	
	private boolean inBoard(double sceneX, double sceneY) {
		return isPicked( board, sceneX, sceneY );
	}
	
	private boolean isPicked(Node n, double sceneX, double sceneY) {
		Bounds bounds = n.getLayoutBounds();
		Bounds boundsScene = n.localToScene( bounds );
		if( (sceneX >= boundsScene.getMinX()) && (sceneY >= boundsScene.getMinY()) &&
				(sceneX <= boundsScene.getMaxX()) && ( sceneY <= boundsScene.getMaxY() ) ) {
			return true;
		}
		return false;
	}
}
