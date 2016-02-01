package com.bekwam.examples.javafx.dynamic;

import com.bekwam.examples.javafx.dynamic.app_core.HomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class MultiStageMain extends Application {

    private Logger logger = LoggerFactory.getLogger(MultiStageMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if( logger.isInfoEnabled() ) {
            logger.info("Starting application");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MultiStageMain.class.getResource("app_core/homeScreen.fxml") );
        Parent p = loader.load();

        HomeScreenController c = (HomeScreenController)loader.getController();

        Application.Parameters parameters = getParameters();
        if( parameters.getRaw().contains("bootstrap=true") ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[START] running in bootstrap mode");
            }
        } else {
            if( logger.isDebugEnabled() ) {
                logger.debug("[START] running in full mode");
            }
            c.initializeSubApps();
        }

        Scene scene = new Scene( p );
        primaryStage.setTitle( "Core" );
        primaryStage.setScene( scene );
        primaryStage.show();

    }
}
