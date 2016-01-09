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
package com.bekwam.examples.javafx.background;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author carl
 *
 */
public class ImprovedBackgroundController {

	private Logger log = LoggerFactory.getLogger( ImprovedBackgroundController.class );
	
	private int BACKGROUND_WIDTH = 2000;
	
	@FXML
	ImageView background1;

	@FXML
	ImageView background2;

	@FXML
	ImageView clouds1;

	@FXML
	ImageView clouds2;

	@FXML
	Button btnControl;

	private ParallelTransition parallelTransition;
	
	@FXML
	public void initialize() {
		
		TranslateTransition background1Transition =
	            new TranslateTransition(Duration.millis(8000), background1);
		background1Transition.setFromX(0);
		background1Transition.setToX(-1 * BACKGROUND_WIDTH);
		background1Transition.setInterpolator(Interpolator.LINEAR);
	
		TranslateTransition background2Transition =
            new TranslateTransition(Duration.millis(8000), background2);
		background2Transition.setFromX(0);
		background2Transition.setToX(-1 * BACKGROUND_WIDTH);
		background2Transition.setInterpolator(Interpolator.LINEAR);

		ParallelTransition backgroundWrapper = new ParallelTransition(
				background1Transition, background2Transition
				);
		backgroundWrapper.setCycleCount(Animation.INDEFINITE);
		
		TranslateTransition clouds1Transition =
	            new TranslateTransition(Duration.millis(20000), clouds1);
		clouds1Transition.setFromX(0);
		clouds1Transition.setToX(-1 * BACKGROUND_WIDTH);
		clouds1Transition.setInterpolator(Interpolator.LINEAR);
	
		TranslateTransition clouds2Transition =
            new TranslateTransition(Duration.millis(20000), clouds2);
		clouds2Transition.setFromX(0);
		clouds2Transition.setToX(-1 * BACKGROUND_WIDTH);
		clouds2Transition.setInterpolator(Interpolator.LINEAR);

		ParallelTransition cloudsWrapper = new ParallelTransition(
				clouds1Transition, clouds2Transition
				);
		cloudsWrapper.setCycleCount(Animation.INDEFINITE);
		
		parallelTransition = 
			new ParallelTransition( backgroundWrapper,
									cloudsWrapper );

		parallelTransition.setCycleCount(Animation.INDEFINITE);

		//
		// Sets the label of the Button based on the animation state
		//
		parallelTransition.statusProperty().addListener((obs, oldValue, newValue) -> {
			if( newValue == Animation.Status.RUNNING ) {
				btnControl.setText( "||" );
			} else {
				btnControl.setText( ">" );
			}
		});
	}
	
	public void startAmination() {
		
		if( log.isDebugEnabled() ) {
			log.debug("[START]");
		}
		parallelTransition.play();
	}
	
	public void pauseAnimation() {
		if( log.isDebugEnabled() ) {
			log.debug("[STOP]");
		}
		parallelTransition.pause();
	}
	
	@FXML
	public void controlPressed() {
		if( log.isDebugEnabled() ) {
			log.debug("[CONTROL PRESSED]");
		}
		if( parallelTransition.getStatus() == Animation.Status.RUNNING ) {
			pauseAnimation();
		} else {
			startAmination();
		}
	}
}
