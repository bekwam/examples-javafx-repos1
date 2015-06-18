/*
 * Copyright 2015 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekwam.jfxbop.guice;

import com.bekwam.jfxbop.view.BaseView;
import com.google.common.base.Preconditions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * A BaseView that pulls in builderFactory and guiceControllerFactory from the environment
 *
 * @author carl_000
 */
public class GuiceBaseView extends BaseView {

    private final Logger logger = LoggerFactory.getLogger(GuiceBaseView.class);

    @Inject
    protected BuilderFactory builderFactory;

    @Inject
    protected GuiceControllerFactory guiceControllerFactory;

    protected Scene scene;

    /**
     * Initializes the object using metadata extracted from the @Viewable annotation
     *
     * Requires injection of a BuilderFactory and GuiceControllerFactory; otherwise NPE will be thrown
     *
     * Calls postInit() at end
     *
     * @throws Exception
     */
    @Override
    protected void init() throws Exception {

        if( logger.isDebugEnabled() ) {
            logger.debug("[SHOW] creating stage");
        }

        readAnnotation();

        stage = new Stage();

        Preconditions.checkNotNull( fxml );
        Preconditions.checkNotNull( builderFactory );
        Preconditions.checkNotNull( guiceControllerFactory );

        Parent p = FXMLLoader.load(getClass().getResource(fxml), null, builderFactory, guiceControllerFactory);

        scene = new Scene(p);

        if( stylesheet != null ) {
            scene.getStylesheets().add(stylesheet);
        }

        if( title != null ) {
            stage.setTitle(title);
        }

        stage.setScene(scene);

        postInit();
    }
}
