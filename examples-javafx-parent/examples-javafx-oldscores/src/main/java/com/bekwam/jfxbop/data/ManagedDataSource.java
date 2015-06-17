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
 * Add this line to your Google Guice module to call this for each of the methods in the data source
 *
 *   bindInterceptor(Matchers.subclassesOf(ManagedDataSource.class), Matchers.any(), new ManagedDataSourceInterceptor());
 *
 * @author carl_000
 */
public interface ManagedDataSource {

    /**
     * Initializes the data source
     *
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * Sets a callback operation to be called after initialization
     *
     * @param initCB
     */
    void setOnPostInit(Consumer<Object> initCB);

    /**
     * Interrogates the data source to determine if the object is initialized
     *
     * @return
     */
    boolean isInitialized();

    /**
     * Refreshes the data source
     *
     * @throws Exception
     */
    void refresh() throws Exception;

    /**
     * Sets a callback operation to be called after refresh
     *
     * @param refreshCB
     */
    void setOnPostRefresh(Consumer<Object> refreshCB);

    /**
     * Interrogates the data source to determine if the object needs to be refreshed
     *
     * @return
     */
    boolean needsRefresh();

    /**
     * Marks the data source for refresh.  At sometime before the next call, the data source will be refreshed.  The
     * refresh may occur immediately.
     *
     */
    void markForRefresh();

    /**
     * Sets a callback operation to cleanup the data source prior to destruction
     *
     * @param destroyCB
     */
    void setOnPreDestroy(Consumer<Object> destroyCB);

    /**
     * A destructor for the data source
     *
     * @throws Exception
     */
    void destroy() throws Exception;
}
