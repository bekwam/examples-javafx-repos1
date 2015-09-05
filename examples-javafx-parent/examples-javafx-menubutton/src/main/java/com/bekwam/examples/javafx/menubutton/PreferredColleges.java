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

import java.util.ArrayList;
import java.util.List;

/**
 * Container domain object of Colleges
 *
 * @author carl_000
 */
public class PreferredColleges {

    private List<College> preferredColleges = new ArrayList<College>();

    public List<College> getPreferredColleges() {
        return preferredColleges;
    }

    public void setPreferredColleges(List<College> preferredColleges) {
        this.preferredColleges = preferredColleges;
    }
}
