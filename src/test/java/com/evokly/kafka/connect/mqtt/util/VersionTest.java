/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionTest {
    @Test
    public void testGetVersion() {
        assertEquals("test_project_version", Version.getVersion());
    }
}
