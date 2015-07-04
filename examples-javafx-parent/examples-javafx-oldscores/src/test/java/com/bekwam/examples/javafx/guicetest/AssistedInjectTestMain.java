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

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class demonstrating a logging subsystem implemented with Google Guice and the AssistedInject extension
 *
 * @author carl_000
 */
public class AssistedInjectTestMain {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new AssistedInjectTestModule());

        LogSubsystem log = injector.getInstance(LogSubsystem.class);

        log.debug("some debugging messages");

        log.info("an info statement");
    }
}
