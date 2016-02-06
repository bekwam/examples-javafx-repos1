package com.bekwam.examples.javafx.dynamic.app_core;

import com.bekwam.examples.javafx.dynamic.api.ServiceObject1;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class CoreModule extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(CoreModule.class);

    private final static String appFolderName = ".examples-javafx-dynamic";
    private final static String subappFolderName = "subapps";
    
    private File appFolder;
    private File subappFolder;

    private final ObservableList<Path> subappJars;
    private final String startupCommandsFullPath;

    public CoreModule(List<Path> subappJars, String startupCommandsFullPath) {
        this.subappJars = FXCollections.observableArrayList(subappJars);
        this.startupCommandsFullPath = startupCommandsFullPath;
    }

    @Override
    protected void configure() {

        if( logger.isInfoEnabled() ) {
            logger.info("[CONFIGURE] Configuring {}", this.getClass().getName());
        }

        String userDir = System.getProperty("user.home");
        
        appFolder = new File(userDir, appFolderName);
        if( !appFolder.exists() ) {
        	boolean retval = appFolder.mkdir();
        	if( logger.isDebugEnabled() ) {
        		logger.debug("[CONFIGURE] create app folder=" + retval);
        	}
        }
        subappFolder = new File( appFolder, subappFolderName );
        if( !subappFolder.exists() ) {
        	boolean retval = subappFolder.mkdir();
        	if( logger.isDebugEnabled() ) {
        		logger.debug("[CONFIGURE] create subapp folder=" + retval);
        	}        	
        }
        
        bind( File.class ).annotatedWith(Names.named("AppFolder")).toInstance(appFolder);
        bind( File.class ).annotatedWith(Names.named("SubAppFolder")).toInstance(subappFolder);
        
        bind( ServiceObject1.class ).to( ServiceObjectImpl1.class ).asEagerSingleton();

        TypeLiteral<ObservableList<Path>> pathsType = new TypeLiteral<ObservableList<Path>>() {};
        bind( pathsType ).annotatedWith(Names.named("SubAppJars")).toInstance(subappJars);

        bind( String.class ).annotatedWith(Names.named("StartupCommandsFullPath")).toInstance(startupCommandsFullPath);
    }
}
