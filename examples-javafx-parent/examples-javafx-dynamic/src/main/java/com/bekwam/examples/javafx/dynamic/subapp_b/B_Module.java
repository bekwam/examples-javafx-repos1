package com.bekwam.examples.javafx.dynamic.subapp_b;

import com.bekwam.examples.javafx.dynamic.annotations.SubApp;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by carl_000 on 2/14/2015.
 */
@SubApp(
        buttonId = "B",
        buttonTitle = "Show B",
        stageTitle = "Screen B",
        fxmlFile = "subapp_b/screenB.fxml"
)
public class B_Module extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger(B_Module.class);

    @Override
    protected void configure() {

        if( logger.isInfoEnabled() ) {
            logger.info("Configuring {}", this.getClass().getName());
        }

        bind( ServiceObject2.class ).to( ServiceObjectImpl2.class ).asEagerSingleton();

    }
}
