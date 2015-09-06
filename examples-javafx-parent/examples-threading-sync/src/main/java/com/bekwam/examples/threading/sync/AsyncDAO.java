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
package com.bekwam.examples.threading.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * A class that simulates an asynchronous data retrieval
 *
 * @author carl_000
 */
public class AsyncDAO {

    private Logger logger = LoggerFactory.getLogger(AsyncDAO.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    public void fetchData(Consumer<String> readyCallback, Consumer<String> failedCallback) {
        if( logger.isDebugEnabled() ) {
            logger.debug("[FETCH]");
        }
        new Thread() {
            public void run() {
                if( logger.isDebugEnabled() ) {
                    logger.debug("[FETCH] in thread id={}", Thread.currentThread().getName());
                }
                try {
                    Thread.sleep(2000);
                    String data = "here is the data " + counter.addAndGet(1);  // if a real impl., catch exception an call failed
                    readyCallback.accept(data);
                } catch(InterruptedException ignore) {
                    failedCallback.accept("fetch was interrupted");
                }
            }
        }.start();
    }

    public static synchronized void reset() {
        counter.set(0);
    }
}
