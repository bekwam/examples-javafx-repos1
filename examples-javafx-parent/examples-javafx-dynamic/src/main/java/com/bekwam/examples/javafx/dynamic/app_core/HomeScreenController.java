package com.bekwam.examples.javafx.dynamic.app_core;

import com.bekwam.examples.javafx.dynamic.annotations.SubApp;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class HomeScreenController {

    private Logger logger = LoggerFactory.getLogger(HomeScreenController.class);

    @FXML
    VBox vbox;

    private final String DEFAULT_PACKAGE_TO_SCAN = "com.bekwam.examples.javafx.dynamic";

    private Injector injector;
    private BuilderFactory builderFactory;
    private Callback<Class<?>, Object> coreGuiceControllerFactory;

    private Map<Object, Stage> stageCache = new LinkedHashMap<>();
    private Map<Object, Callback<Class<?>, Object>> gcfCache = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT]");
        }
        CoreModule module = new CoreModule();
        injector = Guice.createInjector(module);
        builderFactory = new JavaFXBuilderFactory();
        coreGuiceControllerFactory = clazz -> injector.getInstance(clazz);
    }

    public void initializeSubApps() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[INIT SUBAPPS]");
        }

        Reflections reflections = new Reflections(DEFAULT_PACKAGE_TO_SCAN);

        Set<Class<?>> subapps = reflections.getTypesAnnotatedWith(SubApp.class);

        subapps.stream().sorted(Comparator.comparing(Class::getSimpleName)).forEach(sa -> {

            SubApp saAnnotation = sa.getAnnotation(SubApp.class);

            Button btn = new Button(saAnnotation.buttonTitle());
            btn.setId( saAnnotation.buttonId() );
            btn.setUserData(sa);  // holds the metadata
            btn.setOnAction(evt -> startScreen(evt));

            vbox.getChildren().add(btn);
        });
    }

    public void startScreen(ActionEvent evt) {

        if( logger.isDebugEnabled() ) {
            logger.debug("Starting a screen {}", ((Button)evt.getSource()).getId());
        }

        Class<?> sa = (Class<?>)((Button)evt.getSource()).getUserData();

        SubApp saAnnotation = sa.getAnnotation(SubApp.class);

        try {

            Callback<Class<?>, Object> gcf = gcfCache.get( (Button)evt.getSource() );

            if( gcf == null ) {
                AbstractModule m = (AbstractModule)sa.newInstance();
                Injector inj = injector.createChildInjector( m );
                gcf = clazz -> inj.getInstance(clazz);
                gcfCache.put( evt.getSource(), gcf );
            }

            Stage stageToShow = stageCache.get(evt.getSource());
            if (stageToShow == null) {
                Stage stage = createStage( saAnnotation.fxmlFile(), gcf, saAnnotation.stageTitle(), sa );
                stageToShow = stage;
                stageCache.put(evt.getSource(), stageToShow);
            }

            stageToShow.show();

        } catch (Exception exc) {
            logger.error("cannot start a screen", exc);
        }
    }

    private Stage createStage( String fxmlFile, Callback<Class<?>, Object> gcf, String stageTitle, Class<?> sa ) throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[CREATE STAGE] fxmlFile=" + fxmlFile + ", using loader from class=" + sa.getName());
        }

        String path = StringUtils.replace(DEFAULT_PACKAGE_TO_SCAN, ".", "/");

        FXMLLoader aScreenLoader = new FXMLLoader(
                sa.getResource( "/" + path + "/" + fxmlFile ),
                null,
                builderFactory,
                gcf);

        Parent someScreen = (Parent) aScreenLoader.load();

        Stage stage = new Stage();
        stage.setTitle( stageTitle );
        Scene someScene = new Scene(someScreen);
        stage.setScene(someScene);

        return stage;
    }

    @FXML
    public void refreshScreens() {
        gcfCache.clear();
        stageCache.clear();
    }

    @FXML
    public void exitApplication() {

        if( logger.isInfoEnabled() ) {
            logger.info("Exiting application");
        }

        Platform.exit();
    }
}

