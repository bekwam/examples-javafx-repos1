package com.bekwam.examples.javafx.oldscores;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by carl_000 on 5/31/2015.
 */
public class RecenteredDAO {

    private Logger logger = LoggerFactory.getLogger(RecenteredDAO.class);

    private final static String MATH_RECENTERED_JSON_FILE = "/data/mathRecentered.json";
    private final static String VERBAL_RECENTERED_JSON_FILE = "/data/verbalRecentered.json";

    private boolean initialized = false;

    private Scores verbalScores;
    private Scores mathScores;

    public void init() throws IOException {

        BufferedReader br = null;
        Gson gson = new Gson();

        try {
            InputStream is = this.getClass().getResourceAsStream(MATH_RECENTERED_JSON_FILE);
            br = new BufferedReader(new InputStreamReader(is));
            String json = "";
            String line;
            while( (line=br.readLine()) != null ) {
                json += line;
            }
            mathScores = gson.fromJson(json, Scores.class);
        } finally {
            if( br != null ) {
                try {
                    br.close();
                } catch(IOException exc) {
                    if( logger.isWarnEnabled() ) {
                        logger.warn("[INIT] cannot close buffered reader for math json file", exc);
                    }
                }
            }
        }

        try {
            InputStream is = this.getClass().getResourceAsStream(VERBAL_RECENTERED_JSON_FILE);
            br = new BufferedReader(new InputStreamReader(is));
            String json = "";
            String line;
            while( (line=br.readLine()) != null ) {
                json += line;
            }
            verbalScores = gson.fromJson( json, Scores.class );
        } finally {
            if( br != null ) {
                try {
                    br.close();
                } catch(IOException exc) {
                    if( logger.isWarnEnabled() ) {
                        logger.warn("[INIT] cannot close buffered reader for verbal json file", exc);
                    }
                }
            }
        }

        initialized = true;
    }

    public Integer lookup1995MathScore(Integer recenteredScore) {
        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }
        for( Score score : mathScores.getScores() ) {
            if( recenteredScore.equals(score.getRecenteredScore()) ) {
                return score.getPre1995score();
            }
        }

        return 0;
    }

    public Integer lookup1995VerbalScore(Integer recenteredScore) {
        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }
        for( Score score : verbalScores.getScores() ) {
            if( recenteredScore.equals(score.getRecenteredScore()) ) {
                return score.getPre1995score();
            }
        }

        return 0;
    }

    public Integer lookupRecenteredMathScore(Integer score1995) {
        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }
        for( Score score : mathScores.getScores() ) {
            if( score1995.equals(score.getPre1995score()) ) {
                return score.getRecenteredScore();
            }
        }

        return 0;
    }

    public Integer lookupRecenteredVerbalScore(Integer score1995) {

        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }

        for( Score score : verbalScores.getScores() ) {
            if( score1995.equals(score.getPre1995score()) ) {
                return score.getRecenteredScore();
            }
        }
        return 0;
    }

    public String getVerbalAttribution() {
        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }
        return verbalScores.getAttribution();
    }

    public String getMathAttribution() {
        if( !initialized ) {
            throw new IllegalStateException( "dao is not initialized; call init() before calling this method" );
        }
        return mathScores.getAttribution();
    }

    public boolean isInitialized() {
        return initialized;
    }
}
