package com.bekwam.examples.javafx.guicetest;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory for creating LogFormatter objects
 *
 * @author carl_000
 */
public interface LogFormatterFactory {

    LogFormatter create(@Assisted("level") String message);

    LogFormatter create(@Assisted("level") String message, @Assisted("format") String format);

}
