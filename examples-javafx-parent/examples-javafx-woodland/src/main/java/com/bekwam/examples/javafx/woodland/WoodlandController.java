package com.bekwam.examples.javafx.woodland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WoodlandController {

	@FXML
	VBox boardContainer;
	
	@FXML
	Pane board;
	
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
	 */
	private final List<Rectangle> squares = new ArrayList<>();
	
	/**
	 * Effect that will be recycled across squares
	 */
	private final DropShadow dropShadow = new DropShadow();

	/**
	 * Keeps track of square so that it can be un-highlighted
	 */
	private Optional<Rectangle> lastHoverSquare = Optional.empty();
	
	/**
	 * Overall control for whether or not there is a current selection
	 */
	private boolean gpSelected = false;
	
	private double gpCenteringX = 0.0d;
	private double gpCenteringY = 0.0d;
	
	/**
	 * (x,y) coordinates of placement of mouse within the ImageView
	 * 
	 * For snapping
	 */
	private double selectInImageX = 0.0d;
	private double selectInImageY = 0.0d;
	
	/**
	 * Offset for in board check
	 */
	private double topLeftSelectInImageX = 0.0d;
	private double topLeftSelectInImageY = 0.0d;
	
	/**
	 * Calculated value of center coordinate to help with selectInImage*
	 * fields
	 */
	private double gpMidpointX = 0.0d;
	private double gpMidpointY = 0.0d;
	
	private double gpWidth = 0.0d;
	private double gpHeight = 0.0d;
	@FXML
	public void initialize() {
		
		gpCenteringX = gamePiece.getLayoutX();
		gpCenteringY = gamePiece.getLayoutY();
		
		Bounds gpBounds = gamePiece.getBoundsInLocal();
		gpWidth = gpBounds.getWidth();
		gpHeight = gpBounds.getHeight();
		gpMidpointX = (gpWidth)/2;
		gpMidpointY = (gpHeight)/2;
		
		squares.addAll( Arrays.asList(sq0_0, sq0_1, sq0_2, sq1_0, sq1_1, sq1_2, sq2_0, sq2_1, sq2_2) );
		
		board.setOnMousePressed((evt) -> {			
			if( selectGamePiece(evt.getSceneX(), evt.getSceneY()) ) {

				Point2D selectInImageScenePt = new Point2D( evt.getSceneX(), evt.getSceneY() );
				Point2D selectInImagePt = gamePiece.sceneToLocal( selectInImageScenePt );
				
				selectInImageX = gpMidpointX - selectInImagePt.getX();
				selectInImageY = gpMidpointY - selectInImagePt.getY();
				
				topLeftSelectInImageX = selectInImagePt.getX();
				topLeftSelectInImageY = selectInImagePt.getY();
				
				gamePiece.setOpacity( 0.4d );
				gpSelected = true;
				
			}
		});
		
		board.setOnMouseReleased((evt) -> {			
			
			if( gpSelected ) {				
				Optional<Rectangle> onSquare = pickSquare( evt.getSceneX(), evt.getSceneY() );
				snap(onSquare);
			}
			
			clearSelection();  // unconditionally clear in case missed event
		});

		board.setOnMouseDragged( (evt) -> {
			
			if( !inBoard( evt.getSceneX(), evt.getSceneY() ) ) {
				
				//
				// End the drag operation if left the board
				//
				
				MouseEvent releaseEvent = new MouseEvent(
						MouseEvent.MOUSE_RELEASED,
						evt.getSceneX(), 
						evt.getSceneY(),
						evt.getScreenX(), 
						evt.getScreenY(),
						evt.getButton(),
						evt.getClickCount(),
						evt.isShiftDown(),
						evt.isControlDown(),
						evt.isAltDown(),
						evt.isMetaDown(),
						evt.isPrimaryButtonDown(),
						evt.isMiddleButtonDown(),
						evt.isSecondaryButtonDown(),
						true,
						evt.isPopupTrigger(),
						evt.isStillSincePress(),
						evt.getPickResult()						
						);
				
				board.fireEvent( releaseEvent );
			}
			
			if( gpSelected ) {				
				Point2D sceneEvtPt = new Point2D(evt.getSceneX(), evt.getSceneY());
				Point2D parentEvtPt = board.sceneToLocal(sceneEvtPt);
				Insets padding = boardContainer.getPadding();				
				gamePiece.relocate( parentEvtPt.getX()-padding.getLeft()+selectInImageX, parentEvtPt.getY()-padding.getTop()+selectInImageY);					
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

	private void snap(Optional<Rectangle> onSquare) {
		if( onSquare.isPresent() ) {
			
			Point2D onSquarePt = new Point2D(onSquare.get().getLayoutX(), onSquare.get().getLayoutY());
			
			final Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			timeline.setAutoReverse(false);
			timeline.getKeyFrames()
						.add(new KeyFrame(Duration.millis(200), 
								new KeyValue(gamePiece.layoutXProperty(), onSquarePt.getX()+gpCenteringX),
								new KeyValue(gamePiece.layoutYProperty(), onSquarePt.getY()+gpCenteringY))
								);
			timeline.play();
		}
	}

	private void clearSelection() {
		selectInImageX = 0.0d;
		selectInImageY = 0.0d;
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
		
		boolean retval = (
				isPicked( board, sceneX-topLeftSelectInImageX, sceneY-topLeftSelectInImageY ) &&
				isPicked( board, sceneX+(gpWidth-topLeftSelectInImageX), sceneY+(gpHeight-topLeftSelectInImageY))
				);
		
		return retval;
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
