package com.bekwam.examples.javafx.oldscores;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test for ScoresDialogController
 *
 * @author carl_000
 */
public class ScoresDialogControllerTest {

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
