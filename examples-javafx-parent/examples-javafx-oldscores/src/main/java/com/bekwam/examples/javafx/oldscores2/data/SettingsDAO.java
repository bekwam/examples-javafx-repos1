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
package com.bekwam.examples.javafx.oldscores2.data;

import java.io.IOException;

/**
 * DAO for .oldscores flat files
 *
 * @author carl_000
 */
public interface SettingsDAO {

    boolean getRoundUp();

    void setRoundUp(boolean roundUp);

    void save() throws IOException;

    void load() throws IOException ;

    String getAbsolutePath();
}
