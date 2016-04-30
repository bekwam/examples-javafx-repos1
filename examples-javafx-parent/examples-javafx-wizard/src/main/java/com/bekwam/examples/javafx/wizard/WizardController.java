package com.bekwam.examples.javafx.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WizardController {

	private final int INDICATOR_RADIUS = 10;

	@FXML
	VBox contentPanel;

	@FXML
	HBox hboxIndicators;

	private final List<Parent> steps = new ArrayList<>();
	
	private IntegerProperty currentStep = new SimpleIntegerProperty(-1);
	
/*
	@FXML
	public void initialize() throws Exception {

		buildSteps();

		steps.forEach((p) -> hboxIndicators.getChildren().add(createIndicatorCircle()) );

		currentStep.addListener((obs,oldVal,newVal) -> {
			for( int i=0; i<steps.size(); i++ ) {
				Node n = hboxIndicators.getChildren().get(i);
				if( i<= newVal.intValue() ) {
					((Circle)n).setFill( Color.DODGERBLUE );
				} else {
					((Circle)n).setFill( Color.WHITE );
				}
			}});

		currentStep.set( 0 );  // first element

		contentPanel.getChildren().add( steps.get( currentStep.get() ));
	}
*/

	@FXML
	public void initialize() throws Exception {

		buildSteps();

		for( int i=0; i<steps.size(); i++ ) {
			hboxIndicators.getChildren().add( createIndicatorCircle(i));
		}

		currentStep.set( 0 );  // first element

		contentPanel.getChildren().add( steps.get( currentStep.get() ));
	}

	private void buildSteps() throws java.io.IOException {
		Parent step1 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step1.fxml") );
		Parent step2 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step2.fxml") );
		Parent step3 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step3.fxml") );
		Parent confirm = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Confirm.fxml") );
		Parent completed = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Completed.fxml") );

		steps.addAll( Arrays.asList(
				step1, step2, step3, confirm, completed
				));
	}

	private Circle createIndicatorCircle(int i) {

		Circle circle = new Circle(INDICATOR_RADIUS, Color.WHITE);
		circle.setStroke(Color.BLACK);

		circle.fillProperty().bind(
				new When(
						currentStep.greaterThanOrEqualTo(i))
						.then(Color.DODGERBLUE)
						.otherwise(Color.WHITE));

		return circle;
	}

	@FXML
	public void next() {

		if( currentStep.get() < (steps.size()-1) ) {
			contentPanel.getChildren().remove( steps.get(currentStep.get()) );
			currentStep.set( currentStep.get() + 1 );
			contentPanel.getChildren().add( steps.get(currentStep.get()) );
		}
	}

	@FXML
	public void back() {

		if( currentStep.get() > 0 ) {
			contentPanel.getChildren().remove( steps.get(currentStep.get()) );
			currentStep.set( currentStep.get() - 1 );
			contentPanel.getChildren().add( steps.get(currentStep.get()) );
		}
	}
}
