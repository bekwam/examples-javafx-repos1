package com.bekwam.examples.javafx.oldscores;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carl_000 on 5/31/2015.
 */
public class Scores {

    private String attribution;
    private List<Score> scores = new ArrayList<>();

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
