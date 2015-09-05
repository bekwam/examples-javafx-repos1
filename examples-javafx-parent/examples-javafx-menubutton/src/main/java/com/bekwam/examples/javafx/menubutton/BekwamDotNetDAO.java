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
package com.bekwam.examples.javafx.menubutton;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pulls in JSON from a fake web service hosted at www.bekwam.net
 *
 * @author carl_000
 */
public class BekwamDotNetDAO {

    private Logger logger = LoggerFactory.getLogger(BekwamDotNetDAO.class);

    public PreferredColleges findPreferredColleges() {

        if( logger.isDebugEnabled() ) {
            logger.debug("[FIND]");
        }

        Client client = new Client();
        WebResource webResource = client.resource("http://www.bekwam.net/data/colleges.json");
        String json = webResource.get(String.class);
        PreferredColleges retval = new Gson().fromJson(json, PreferredColleges.class);

        if( logger.isDebugEnabled() ) {
            logger.debug("[FIND] json=" + retval);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {}

        return retval;
    }
}
