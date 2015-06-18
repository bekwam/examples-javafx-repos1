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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test for ScoresViewController
 *
 * @author carl_000
 */
public class ScoresViewControllerTest {

    private Mockery context = new Mockery();

    private ScoresDialogController controller;
    private SettingsDAO settingsDAO;

    @Before
    public void init() {

        settingsDAO = context.mock(SettingsDAO.class);

        controller = new ScoresDialogController();
        controller.setSettingsDAO(settingsDAO);
    }

    @Test
    public void noNeedRound() {
        assertFalse(controller.needsRound(680));
        context.assertIsSatisfied();
    }

    @Test
    public void needsRound() {
        assertTrue(controller.needsRound(657));
        context.assertIsSatisfied();
    }

    @Test
    public void roundUp() {
        context.checking(new Expectations() {{
            oneOf(settingsDAO).getRoundUp(); will(returnValue(true));
        }});

        assertEquals( (Integer)680, (Integer)controller.round(672) );
        context.assertIsSatisfied();
    }

    @Test
    public void roundDown() {
        context.checking(new Expectations() {{
            oneOf(settingsDAO).getRoundUp(); will(returnValue(false));
        }});
        assertEquals( (Integer)670, (Integer)controller.round(672) );
        context.assertIsSatisfied();
    }
}
