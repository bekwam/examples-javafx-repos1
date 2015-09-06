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

/**
 * A class that calls AsyncDAO, but offers a synchronous API
 *
 * Waits on AsyncDAO's fetch
 *
 * @author carl_000
 */
public class SyncDAOWrapperWaitNotify {

    private Logger logger = LoggerFactory.getLogger(AsyncDAO.class);

    String data = "";
    String errorMessage = "";
    boolean dataAvailable = false;
    boolean wasError = false;

    AsyncDAO ad = new AsyncDAO();

    public String fetchData() throws SyncDAOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[FETCH] thread id={}", Thread.currentThread().getName());
        }

        ad.fetchData(
                    (d) -> dataReady(d),
                    (e) -> error(e)
        );

        try {
            synchronized(this) {
                wait(30000);
           }
        } catch(InterruptedException ignore) {}

        if( wasError ) {
            String savedMessage = errorMessage;
            reset();
            throw new SyncDAOException( savedMessage );
        }

        String savedData = data;
        reset();

        return savedData;
    }

    private synchronized void reset() {
        data = "";
        errorMessage = "";
        dataAvailable = false;
        wasError = false;
    }

    private synchronized void dataReady(String d) {
        data = d;
        dataAvailable = true;
        notify();
    }

    private synchronized void error(String message) {
        errorMessage = message;
        wasError = true;
        notify();
    }
}
