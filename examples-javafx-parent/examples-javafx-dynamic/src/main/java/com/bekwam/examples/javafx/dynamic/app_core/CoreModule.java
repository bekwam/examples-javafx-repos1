package com.bekwam.examples.javafx.dynamic.app_core;

import com.bekwam.examples.javafx.dynamic.api.ServiceObject1;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class CoreModule extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(CoreModule.class);

    @Override
    protected void configure() {

        if( logger.isInfoEnabled() ) {
            logger.info("Configuring {}", this.getClass().getName());
        }

        bind( ServiceObject1.class ).to( ServiceObjectImpl1.class ).asEagerSingleton();
    }
}
