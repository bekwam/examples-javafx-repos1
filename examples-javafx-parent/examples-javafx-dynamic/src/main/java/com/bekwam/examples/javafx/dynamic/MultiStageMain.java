package com.bekwam.examples.javafx.dynamic;

import com.bekwam.examples.javafx.dynamic.app_core.HomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class MultiStageMain extends Application {

    private Logger logger = LoggerFactory.getLogger(MultiStageMain.class);

    private final static String APP_FOLDER_NAME = ".examples-javafx-dynamic";
    private final static String SUBAPP_FOLDER_NAME = "subapps";
	private final static String STARTUP_COMMANDS_FILE_NAME = "startup.cds";

    private ClassLoader urlClassLoader;
    private URL[] urls;
	private final List<Path> subappJars = new ArrayList<>();
	private String startupCommandsFullPath;

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
            
            //
            // Build runtime classpath from .examples-javafx-dynamic/subapps
            // folder contents
            //
            buildURLClassLoader();

			//
			// Initializes home object w. args for Guice Module
			//
			c.initializeHome(subappJars, startupCommandsFullPath);

            //
            // Will additionally scan packages for modules to load with
            // Reflections library
            //
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

	/**
	 * @throws MalformedURLException
	 */
	private void buildURLClassLoader() throws MalformedURLException {
		
		String userDir = System.getProperty("user.home");
		
		// refactor this similar code from CoreModule.java
		File appFolder = new File(userDir, APP_FOLDER_NAME);
		if( !appFolder.exists() ) {
			boolean retval = appFolder.mkdir();
			if( logger.isDebugEnabled() ) {
				logger.debug("[BUILD] create app folder=" + retval);
			}
		}

		//
		// Look for startup.cmd to clear out JARs flagged for deletion
		//
		File[] appFolderFiles = appFolder.listFiles((pathname) -> pathname.getName().equalsIgnoreCase(STARTUP_COMMANDS_FILE_NAME));
		if( appFolderFiles != null && appFolderFiles.length > 0 ) {
			if( logger.isDebugEnabled() ) {
				logger.debug("[BUILD] found command file");
			}
			runCommandFile( appFolderFiles[0] );  // not possible to have >1 match
		}

		startupCommandsFullPath = Paths.get( appFolder.getAbsolutePath(), STARTUP_COMMANDS_FILE_NAME ).toString();

		File subappFolder = new File( appFolder, SUBAPP_FOLDER_NAME);
		if( !subappFolder.exists() ) {
			boolean retval = subappFolder.mkdir();
			if( logger.isDebugEnabled() ) {
				logger.debug("[BUILD] create subapp folder=" + retval);
			}        	
		}
		
		List<URL> jarURLs = new ArrayList<>();
		File[] subappJARs = subappFolder.listFiles();
		for( File sa : subappJARs ) {
			String urlPath = "jar:file:" + sa.getPath() + "!/";
			logger.debug("[BUILD] url=" + urlPath);
			jarURLs.add( new URL(urlPath) );
			subappJars.add( sa.toPath() );
		}
		
		if( jarURLs.size() > 0 ) {
			if( logger.isDebugEnabled() ) {
				logger.debug("[BUILD] creating class loader");
			}
			urls = jarURLs.toArray( new URL[0] );
			
			urlClassLoader = new URLClassLoader( urls, this.getClass().getClassLoader() );
		} else {
			urlClassLoader = this.getClass().getClassLoader();
		}
	}

	private void runCommandFile(File f) {

		try (
				BufferedReader br = new BufferedReader(new FileReader(f))
				) {

			String line = null;
			while( (line=br.readLine()) != null) {

				if( line.length() < 3 ) {
					logger.error("error parsing command file; line=" + line + " not valid");
					break;
				}

				String command = line.substring(0, 1);
				String arg = line.substring(2, line.length());

				if( command.equalsIgnoreCase("D") ) {
					Path p = Paths.get(arg);
					if( logger.isDebugEnabled() ) {
						logger.debug("[RUN CMD] deleting file=" + p.getFileName());
					}
					Files.delete(p);
				}
			}

		} catch(IOException exc) {

			logger.error( "error processing command file " + f.getName(), exc );
		}

		boolean retval = f.delete();  // delete the commands file when finished so that it won't run again

		if( logger.isDebugEnabled() ) {
			logger.debug("[RUN CMD] retval=" + retval);
		}

	}
}
