package com.bekwam.examples.javafx.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class WizardController {

	private final int INDICATOR_RADIUS = 10;

	@FXML
	VBox contentPanel;

	@FXML
	HBox hboxIndicators;

	@FXML
	Button btnNext, btnBack, btnCancel;

	@Inject
	Injector injector;

	@Inject
	WizardData model;

	private final List<Parent> steps = new ArrayList<>();
	
	private final IntegerProperty currentStep = new SimpleIntegerProperty(-1);

	@FXML
	public void initialize() throws Exception {

		buildSteps();

		initButtons();

		buildIndicatorCircles();

		setInitialContent();
	}

	private void initButtons() {
		btnBack.disableProperty().bind( currentStep.lessThanOrEqualTo(0) );
		btnNext.disableProperty().bind( currentStep.greaterThanOrEqualTo(steps.size()-1) );

		btnCancel.textProperty().bind(
				new When(
						currentStep.lessThan(steps.size()-1)
				)
						.then("Cancel")
						.otherwise("Start Over")
		);
	}

	private void setInitialContent() {
		currentStep.set( 0 );  // first element
		contentPanel.getChildren().add( steps.get( currentStep.get() ));
	}

	private void buildIndicatorCircles() {
		for( int i=0; i<steps.size(); i++ ) {
			hboxIndicators.getChildren().add( createIndicatorCircle(i));
		}
	}

	private void buildSteps() throws java.io.IOException {

		final JavaFXBuilderFactory bf = new JavaFXBuilderFactory();

		final Callback<Class<?>, Object> cb = (clazz) -> injector.getInstance(clazz);

		Parent step1 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step1.fxml"), null, bf, cb );
		Parent step2 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step2.fxml"), null, bf, cb );
		Parent step3 = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Step3.fxml"), null, bf, cb );
		Parent confirm = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Confirm.fxml"), null, bf, cb );
		Parent completed = FXMLLoader.load( WizardController.class.getResource("/wizard-fxml/Completed.fxml"), null, bf, cb);

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

	@FXML
	public void cancel() {

		contentPanel.getChildren().remove( steps.get(currentStep.get()) );
		currentStep.set( 0 );  // first screen
		contentPanel.getChildren().add( steps.get(currentStep.get()) );

		model.reset();
	}
}
