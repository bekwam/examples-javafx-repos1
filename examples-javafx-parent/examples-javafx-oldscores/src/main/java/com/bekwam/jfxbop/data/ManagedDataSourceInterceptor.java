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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calls a method's lazyInit routine on a class' method (but not on init itself)
 *
 * It's up to the implementor of ManagedDataSource to correctly handle the init(), refresh(), and destroy() cases.
 * This includes tracking the state so that the query functions (needsRefresh(), isInitialized()) work.  The
 * handling of the callback setters is optional.
 *
 * Add this line to your Google Guice module to call this for each of the methods in the data source
 *
 *   bindInterceptor(Matchers.subclassesOf(ManagedDataSource.class), Matchers.any(), new ManagedDataSourceInterceptor());
 *
 * @author carl_000
 */
public class ManagedDataSourceInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ManagedDataSourceInterceptor.class);

    private static final ThreadLocal<Boolean> disabled = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;  // default for ThreadLocal is null otherwise
        }
    };

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        if( disabled.get() ) {
            if( logger.isDebugEnabled() ) {
                logger.debug("[INVOKE] interceptor is disabled; skipping");
            }
            return methodInvocation.proceed();
        }

        Object thisObj = methodInvocation.getThis();

        if (thisObj instanceof ManagedDataSource) {

            ManagedDataSource ds = (ManagedDataSource) thisObj;
            if( logger.isDebugEnabled() ) {
                logger.debug("[INVOKE] disabling interceptor while going through ds api");
            }
            disabled.set(true);
            try {
                if (!ds.isInitialized()) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("[INVOKE] datasource {} needs initialization; calling init",
                                thisObj.getClass().getName());
                    }
                    ds.init();
                }
                if (ds.needsRefresh()) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("[INVOKE] datasource {} needs refresh; calling refresh",
                                thisObj.getClass().getName());
                    }

                    ds.refresh();
                }
            } finally {
                if( logger.isDebugEnabled() ) {
                    logger.debug("[INVOKE] re-enabling interceptor");
                }
                disabled.set(false);
            }
        }

        return methodInvocation.proceed();
    }
}
