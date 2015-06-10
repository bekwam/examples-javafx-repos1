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
 * Created by carl_000 on 5/31/2015.
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
