package com.bekwam.examples.javafx.dynamic.subapp_b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ServiceObjectImpl2 implements ServiceObject2 {

    private Logger logger = LoggerFactory.getLogger(ServiceObjectImpl2.class);

    @Override
    public void doSomething2() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[DO SOMETHING 2] object id={}", this.hashCode());
        }
    }
}
