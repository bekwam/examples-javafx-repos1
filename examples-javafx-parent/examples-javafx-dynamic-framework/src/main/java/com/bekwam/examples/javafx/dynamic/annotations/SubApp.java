package com.bekwam.examples.javafx.dynamic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by carl_000 on 2/14/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubApp {

    public String buttonId();
    public String buttonTitle();
    public String stageTitle();
    public String fxmlFile();
}
