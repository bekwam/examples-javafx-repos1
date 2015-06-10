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
package com.bekwam.examples.javafx.oldscores;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of DAO for .oldscores files
 *
 * @author carl_000
 */
public class SettingsDAOImpl implements SettingsDAO {

    private Logger logger = LoggerFactory.getLogger(SettingsDAOImpl.class);

    private Settings settings = new Settings();

    private final static String SETTINGS_FILE_NAME = ".oldscores";

    @Override
    public boolean getRoundUp() {
        return settings.getRoundUp();
    }

    @Override
    public void setRoundUp(boolean roundUp) {
        settings.setRoundUp(roundUp);
    }

    @Override
    public void save() throws IOException {

        if( logger.isDebugEnabled() ) {
            logger.debug("[SAVE] saving " + getAbsolutePath() );
        }
        FileWriter fw = new FileWriter( getAbsolutePath() );
        Properties props = new Properties();
        props.setProperty("oldscores.roundUp", String.valueOf(settings.getRoundUp()));
        props.store(fw, "");
        fw.close();
    }

    @Override
    public void load() throws IOException {

        File f = new File(getAbsolutePath());
        if( f.exists() ) {

            if( logger.isDebugEnabled() ) {
                logger.debug("[LOAD] " + f.getName() + " exists; loading");
            }
            FileReader fr = new FileReader(f);
            Properties props = new Properties();
            props.load(fr);
            settings.setRoundUp(BooleanUtils.toBoolean(props.getProperty("oldscores.roundUp")));
            fr.close();
        } else {
            if( logger.isDebugEnabled() ) {
                logger.debug("[LOAD " + f.getName() + " does not exist");
            }
        }

    }

    @Override
    public String getAbsolutePath() {
        File f = new File( System.getProperty("user.home"), SETTINGS_FILE_NAME );
        return f.getAbsolutePath();
    }
}
