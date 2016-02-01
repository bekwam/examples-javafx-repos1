package com.bekwam.examples.javafx.dynamic.app_core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ServiceObjectImpl1 implements ServiceObject1 {

    private Logger logger = LoggerFactory.getLogger(ServiceObjectImpl1.class);

    @Override
    public void doSomething() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[DO SOMETHING] object id={}", this.hashCode());
        }
    }
}
