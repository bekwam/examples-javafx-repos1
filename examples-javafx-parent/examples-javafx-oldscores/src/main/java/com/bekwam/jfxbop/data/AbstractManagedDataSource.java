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
package com.bekwam.jfxbop.data;

import java.util.function.Consumer;

/**
 * Lifecycle methods for working with data sources
 *
 * @author carl_000
 */
abstract public class AbstractManagedDataSource implements ManagedDataSource {

    protected Consumer<Object> initCB;
    protected Consumer<Object> refreshCB;
    protected Consumer<Object> destroyCB;

    @Override
    public void setOnPostInit(Consumer<Object> initCB) { this.initCB = initCB; }

    @Override
    public void setOnPostRefresh(Consumer<Object> refreshCB) { this.refreshCB = refreshCB; }

    @Override
    public void setOnPreDestroy(Consumer<Object> destroyCB) { this.destroyCB = destroyCB; }

    @Override
    public void destroy() throws Exception {}
}
