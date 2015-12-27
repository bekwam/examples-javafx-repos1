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
public class BackgroundController {

	private Logger log = LoggerFactory.getLogger( BackgroundController.class );
	
	private int BACKGROUND_WIDTH = 2000;
	
	@FXML
	ImageView background1;

	@FXML
	ImageView background2;

	@FXML
	Button btnControl;

	private ParallelTransition parallelTransition;
	
	@FXML
	public void initialize() {
		
		TranslateTransition translateTransition =
	            new TranslateTransition(Duration.millis(10000), background1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition.setInterpolator(Interpolator.LINEAR);
	
		TranslateTransition translateTransition2 =
            new TranslateTransition(Duration.millis(10000), background2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		parallelTransition = 
			new ParallelTransition( translateTransition, translateTransition2 );
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
