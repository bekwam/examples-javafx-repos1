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
public interface SettingsDAO {

    public boolean getRoundUp();

    public void setRoundUp(boolean roundUp);

    public void save() throws IOException;

    public void load() throws IOException ;

    public String getAbsolutePath();
}
