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
package com.bekwam.examples.javafx.guicetest;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import javax.inject.Inject;

/**
 * Implementation of LogFormatter
 *
 * @AssistedInject used to pull in values at runtime from the caller
 *
 * @Inject on LogConfig shows that this object, returned from the LogFormatterFactory, will have its dependencies
 * pulled in via Guice
 *
 * @author carl_000
 */
public class LogFormatterImpl implements LogFormatter {

    private String format = "'%s'";
    private String level;

    @Inject
    LogConfig logConfig;

    @AssistedInject
    public LogFormatterImpl(@Assisted("level") String level) {
        this.level = level;
    }

    @AssistedInject
    public LogFormatterImpl(@Assisted("level") String level, @Assisted("format") String format) {
        this(level);
        this.format = format;
    }

    @Override
    public String format(String message) {
        String formattedMessage = String.format( format, message);
        String retval = "[" + level.toUpperCase() + "] - " + formattedMessage;
        if( logConfig.useUpperCaseForLevel() ) {
            return retval.toUpperCase();
        } else {
            return retval.toLowerCase();
        }
    }
}

