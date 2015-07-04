package com.bekwam.examples.javafx.guicetest;

/**
 * API for the LogSubsystem
 *
 * Writes a formatted message out to stdout
 *
 * @author carl_000
 */
public interface LogSubsystem {

    void debug(String message);

    void info(String message);

}
