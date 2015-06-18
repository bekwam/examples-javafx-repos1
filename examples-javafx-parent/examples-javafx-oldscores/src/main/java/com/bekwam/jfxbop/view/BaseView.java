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
package com.bekwam.jfxbop.view;

import com.google.common.base.Preconditions;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for a secondary Stage
 *
 * @author carl_000
 */
public class BaseView {

    private final Logger logger = LoggerFactory.getLogger(BaseView.class);

    protected Stage stage;
    protected String fxml = "";
    protected String stylesheet = "";
    protected String title = "";

    public void show() throws Exception {

        if( stage == null ) {
            init();
        }

        if( !stage.isShowing() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[SHOW] stage is not showing");
            }
            stage.show();
        }
    }


    public void hide() throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("[HIDE]");
        }

        if (stage != null && stage.isShowing()) {
            if (logger.isDebugEnabled()) {
                logger.debug("[HIDE] stage is not null and is showing");
            }
            stage.hide();
        }
    }

    protected void init() throws Exception {}

    protected void postInit() throws Exception {}

    protected void readAnnotation() {
        Viewable viewable = this.getClass().getAnnotation(Viewable.class);
        if( viewable != null ) {
            fxml = Preconditions.checkNotNull(viewable.fxml());  // fast fail
            stylesheet = viewable.stylesheet();
            title = viewable.title();
        }
    }
}
