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

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

/**
 * Guice bindings
 *
 * @author carl_000
 */
public class AssistedInjectTestModule extends AbstractModule {

    public void configure() {

        bind(LogSubsystem.class).to(LogSubsystemImpl.class);
        bind(LogConfig.class).to(LogConfigImpl.class);

        bind(Boolean.class).annotatedWith(Names.named("UseUpperCase")).toInstance(Boolean.TRUE);

        install(new FactoryModuleBuilder()
                .implement(LogFormatter.class, LogFormatterImpl.class)
                .build(LogFormatterFactory.class));

    }
}
