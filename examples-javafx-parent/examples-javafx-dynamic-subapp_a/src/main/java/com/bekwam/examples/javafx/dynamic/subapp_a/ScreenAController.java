package com.bekwam.examples.javafx.dynamic.subapp_a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bekwam.examples.javafx.dynamic.api.ServiceObject1;
import com.google.inject.Inject;

import javafx.fxml.FXML;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ScreenAController {

    private Logger logger = LoggerFactory.getLogger(ScreenAController.class);

    @Inject
    private ServiceObject1 service;

    public ScreenAController() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[CONSTRUCTOR}");
        }
    }

    @FXML
    public void doSomethingOnA() {

        service.doSomething();
    }
}
