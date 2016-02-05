package com.bekwam.examples.javafx.dynamic;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bekwam.examples.javafx.dynamic.app_core.HomeScreenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class MultiStageMain extends Application {

    private Logger logger = LoggerFactory.getLogger(MultiStageMain.class);

    private final static String appFolderName = ".examples-javafx-dynamic";
    private final static String subappFolderName = "subapps";

    private ClassLoader urlClassLoader;
    private URL[] urls;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if( logger.isInfoEnabled() ) {
            logger.info("Starting application");
        }

        Application.Parameters parameters = getParameters();

        //
        // Build runtime classpath from .examples-javafx-dynamic/subapps
        // folder contents
        //
        if( !parameters.getRaw().contains("bootstrap=true") ) {
	        String userDir = System.getProperty("user.home");
	        
	        // refactor this similar code from CoreModule.java
	        File appFolder = new File(userDir, appFolderName);
	        if( !appFolder.exists() ) {
	        	boolean retval = appFolder.mkdir();
	        	if( logger.isDebugEnabled() ) {
	        		logger.debug("[CONFIGURE] create app folder=" + retval);
	        	}
	        }
	        File subappFolder = new File( appFolder, subappFolderName );
	        if( !subappFolder.exists() ) {
	        	boolean retval = subappFolder.mkdir();
	        	if( logger.isDebugEnabled() ) {
	        		logger.debug("[CONFIGURE] create subapp folder=" + retval);
	        	}        	
	        }
	        
	        List<URL> jarURLs = new ArrayList();
	        File[] subappJARs = subappFolder.listFiles();
	        for( File sa : subappJARs ) {
	        	String urlPath = "jar:file:" + sa.getPath() + "!/";
	        	logger.debug("[START] url=" + urlPath);
	        	jarURLs.add( new URL(urlPath) );
	        }
	        
	        if( jarURLs.size() > 0 ) {
	        	if( logger.isDebugEnabled() ) {
	        		logger.debug("[START] creating class loader");
	        	}
	        	urls = jarURLs.toArray( new URL[0] );
	        	
	        	urlClassLoader = new URLClassLoader( urls, this.getClass().getClassLoader() );
	        } else {
	        	urlClassLoader = this.getClass().getClassLoader();
	        }
        }
        
        
        //
        // Create initial screen; initialize subapps (found on classpath)
        //
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MultiStageMain.class.getResource("app_core/homeScreen.fxml") );
        Parent p = loader.load();

        HomeScreenController c = (HomeScreenController)loader.getController();

        if( parameters.getRaw().contains("bootstrap=true") ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[START] running in bootstrap mode");
            }
        } else {
            if( logger.isDebugEnabled() ) {
                logger.debug("[START] running in full mode");
            }
            c.initializeSubApps(urlClassLoader, urls);
        }

        //
        // Show main screen
        //        
        Scene scene = new Scene( p );
        primaryStage.setTitle( "Core" );
        primaryStage.setScene( scene );
        primaryStage.show();

    }
}
