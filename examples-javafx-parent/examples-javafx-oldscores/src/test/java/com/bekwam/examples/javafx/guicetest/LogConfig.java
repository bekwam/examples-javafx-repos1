package com.bekwam.examples.javafx.guicetest;

/**
 * Object that configures the logging subsystem
 *
 * Currently, just a single value bound in the AbstractModule
 *
 * @author carl_000
 */
public interface LogConfig {

    boolean useUpperCaseForLevel();
}
