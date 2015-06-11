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
package com.bekwam.examples.javafx.oldscores1;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.BuilderFactory;

/**
 * Google Guice module for application
 *
 * @author carl_000
 */
public class OldScoresModule extends AbstractModule {

    final private static String MATH_RECENTERED_JSON_FILE = "/data/mathRecentered.json";
    final private static String VERBAL_RECENTERED_JSON_FILE = "/data/verbalRecentered.json";
    final private static String SETTINGS_FILE_NAME = ".oldscores";

    @Override
    protected void configure() {

        String mathRecenteredJSONFile = MATH_RECENTERED_JSON_FILE;
        String verbalRecenteredJSONFile = VERBAL_RECENTERED_JSON_FILE;
        String settingsFileName = SETTINGS_FILE_NAME;

        bind(BuilderFactory.class).to(JavaFXBuilderFactory.class);

        bind(String.class).annotatedWith(Names.named("mathRecenteredJSONFile")).toInstance(mathRecenteredJSONFile);
        bind(String.class).annotatedWith(Names.named("verbalRecenteredJSONFile")).toInstance(verbalRecenteredJSONFile);
        bind(String.class).annotatedWith(Names.named("settingsFileName")).toInstance(settingsFileName);

        bind(SettingsDAO.class).to(SettingsDAOImpl.class).asEagerSingleton();
        bind(RecenteredDAO.class).to(RecenteredDAOImpl.class).asEagerSingleton();
    }
}
