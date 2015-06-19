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
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for a secondary Stage
 *
 * @author carl_000
 */
public class BaseView implements View{

    private final Logger logger = LoggerFactory.getLogger(BaseView.class);

    protected Stage stage;
    protected String fxml = "";
    protected String stylesheet = "";
    protected String title = "";

    /**
     * Shows the Stage on which this View is based
     * <p>
     * Will call init() if the object has not been initialized
     *
     * @throws Exception
     */
    @Override
    public void show() throws Exception {

        if (stage == null) {
            init();
        }

        if (!stage.isShowing()) {
            if (logger.isDebugEnabled()) {
                logger.debug("[SHOW] stage is not showing");
            }
            stage.show();
        }
    }


    /**
     * Hides the Stage if it's not showing
     *
     * @throws Exception
     */
    @Override
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

    /**
     * Initializes the object
     *
     * @throws Exception
     */
    protected void init() throws Exception {
    }

    /**
     * Method to be called after the init() operations are completed
     *
     * @throws Exception
     */
    protected void postInit() throws Exception {
    }

    /**
     * Sets the object based on the class' @Viewable annotation
     * <p>
     * If the annotation isn't present, this operation doesn't do anything
     */
    protected void readAnnotation() {
        Viewable viewable = this.getClass().getAnnotation(Viewable.class);
        if (viewable != null) {
            fxml = Preconditions.checkNotNull(viewable.fxml());  // fast fail
            stylesheet = viewable.stylesheet();
            title = viewable.title();
        }
    }

    /**
     * Gets the FXML file used in the construction of the View's contents
     *
     * @return
     */
    public String getFxml() {
        return fxml;
    }

    /**
     * Sets the FXML file used in the construction of the View's contents
     *
     * fxml parameter cannot be null
     *
     * @return
     */
    public void setFxml(String fxml) {
        Preconditions.checkNotNull( fxml );
        this.fxml = fxml;
    }

    /**
     * Gets the stylesheet to be applied to the View
     *
     * @return
     */
    public String getStylesheet() {
        return stylesheet;
    }

    /**
     * Sets the stylesheet to be applied to the View
     *
     * @return
     */
    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    /**
     * Gets the title of the View
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the View
     *
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }
}

