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
package com.bekwam.examples.javafx.oldscores1;

/**
 * Object for score entry
 *
 * @author carl_000
 */
public class Score {

    private Integer pre1995score;
    private Integer recenteredScore;

    public Integer getPre1995score() {
        return pre1995score;
    }

    public void setPre1995score(Integer pre1995score) {
        this.pre1995score = pre1995score;
    }

    public Integer getRecenteredScore() {
        return recenteredScore;
    }

    public void setRecenteredScore(Integer recenteredScore) {
        this.recenteredScore = recenteredScore;
    }
}
