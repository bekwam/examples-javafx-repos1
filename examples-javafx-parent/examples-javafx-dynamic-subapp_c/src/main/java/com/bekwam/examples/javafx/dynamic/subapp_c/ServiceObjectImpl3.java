package com.bekwam.examples.javafx.dynamic.subapp_c;

import com.bekwam.examples.javafx.dynamic.subapp_c.ServiceObject3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ServiceObjectImpl3 implements ServiceObject3 {

    private Logger logger = LoggerFactory.getLogger(ServiceObjectImpl3.class);

    @Override
    public void doSomething3() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[DO SOMETHING 3] object id={}", this.hashCode());
        }
    }
}
