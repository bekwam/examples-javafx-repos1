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

import javax.inject.Inject;

/**
 * Implements LogSubsystem
 *
 * @author carl_000
 */
public class LogSubsystemImpl implements LogSubsystem {

    @Inject
    LogFormatterFactory logFormatterFactory;

    @Override
    public void debug(String message) {
        LogFormatter fmt = logFormatterFactory.create("DEBUG");
        String formattedMessage = fmt.format(message);
        System.out.println(formattedMessage);
    }

    @Override
    public void info(String message) {
        LogFormatter fmt2 = logFormatterFactory.create("INFO",  "msg=%s");
        String formattedMessage2 = fmt2.format(message);
        System.out.println(formattedMessage2);
    }

}
