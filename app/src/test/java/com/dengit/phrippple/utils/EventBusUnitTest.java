package com.dengit.phrippple.utils;

import com.dengit.phrippple.utils.EventBus;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class EventBusUnitTest {

    @Test
    public void eventBusInstanceNotNull() throws Exception {
        //        assertEquals(4, 2 + 2);
        assertNotNull("eventbus instance can not be null", EventBus.getInstance());
    }
}