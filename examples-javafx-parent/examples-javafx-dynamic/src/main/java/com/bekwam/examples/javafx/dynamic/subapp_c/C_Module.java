package com.bekwam.examples.javafx.dynamic.subapp_c;

import com.bekwam.examples.javafx.dynamic.annotations.SubApp;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
@SubApp(
        buttonId = "C",
        buttonTitle = "Show C",
        stageTitle = "Screen C",
        fxmlFile = "subapp_c/screenC.fxml"
)
public class C_Module extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(C_Module.class);

    @Override
    protected void configure() {

        if( logger.isInfoEnabled() ) {
            logger.info("Configuring {}", this.getClass().getName());
        }

        bind( ServiceObject3.class ).to( ServiceObjectImpl3.class ).asEagerSingleton();

    }
}
