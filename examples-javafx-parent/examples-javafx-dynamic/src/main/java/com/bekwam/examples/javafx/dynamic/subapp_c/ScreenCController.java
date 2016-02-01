package com.bekwam.examples.javafx.dynamic.subapp_c;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
public class ScreenCController {

    private Logger logger = LoggerFactory.getLogger(ScreenCController.class);

    @Inject
    private ServiceObject3 service;

    public ScreenCController() {
        if( logger.isDebugEnabled() ) {
            logger.debug("[CONSTRUCTOR}");
        }
    }

    @FXML
    public void doSomethingOnC() {

        service.doSomething3();
    }

}
