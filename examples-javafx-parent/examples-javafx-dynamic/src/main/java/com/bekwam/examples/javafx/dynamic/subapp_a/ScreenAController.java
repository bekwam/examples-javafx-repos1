package com.bekwam.examples.javafx.dynamic.subapp_a;

import com.bekwam.examples.javafx.dynamic.app_core.ServiceObject1;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
