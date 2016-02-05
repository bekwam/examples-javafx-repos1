package com.bekwam.examples.javafx.dynamic.app_core;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bekwam.examples.javafx.dynamic.api.ServiceObject1;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class CoreModule extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(CoreModule.class);

    private final static String appFolderName = ".examples-javafx-dynamic";
    private final static String subappFolderName = "subapps";
    
    private File appFolder;
    private File subappFolder;
    
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
        
    }
}
