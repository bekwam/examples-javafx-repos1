package com.bekwam.jfxbop.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marking a Stage-based object as a Viewable and contains configuration information for the object
 *
 * @author carl_000
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Viewable {

    /**
     * The FXML file used to build the contents of the View
     *
     * @return
     */
    String fxml();

    /**
     * The stylesheet to be applied to the View (optional)
     *
     * @return
     */
    String stylesheet();

    /**
     * The title of the View (optional)
     *
     * @return
     */
    String title();
}
