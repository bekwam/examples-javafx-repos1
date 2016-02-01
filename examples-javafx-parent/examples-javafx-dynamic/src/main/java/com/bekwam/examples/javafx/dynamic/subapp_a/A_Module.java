package com.bekwam.examples.javafx.dynamic.subapp_a;

import com.bekwam.examples.javafx.dynamic.annotations.SubApp;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
@SubApp(
        buttonId = "A",
        buttonTitle = "Show A",
        stageTitle = "Screen A",
        fxmlFile = "subapp_a/screenA.fxml"
)
public class A_Module extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(A_Module.class);

    @Override
    protected void configure() {

        if( logger.isInfoEnabled() ) {
            logger.info("Configuring {}", this.getClass().getName());
        }
    }
}
