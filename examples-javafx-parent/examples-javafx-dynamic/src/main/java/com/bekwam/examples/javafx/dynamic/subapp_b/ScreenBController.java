package com.bekwam.examples.javafx.dynamic.subapp_b;

import com.bekwam.examples.javafx.dynamic.app_core.ServiceObject1;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ScreenBController {

    private Logger logger = LoggerFactory.getLogger(ScreenBController.class);

    @Inject
    private ServiceObject2 service;

    @Inject
    private ServiceObject1 service1;

    public ScreenBController() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[CONSTRUCTOR}");
        }
    }
    @FXML
    public void doSomethingOnB() {

        service.doSomething2();

        service1.doSomething();
    }

}
