/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License</p>
 **/

package com.evokly.kafka.connect.mqtt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public class Version {
    private static final Logger log = LoggerFactory.getLogger(Version.class);
    private static String version = "unknown";

    static {
        InputStream in = null;
        try {
            Properties props = new Properties();
            in = Version.class.getResourceAsStream(
                    "/kafka-connect-mqtt-version.properties");
            props.load(in);
            version = props.getProperty("version", version).trim();
            in.close();
        } catch (Exception e) {
            log.warn("Error while loading version:", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.warn("WTF!", e);
                }
            }
        }
    }

    public static String getVersion() {
        return version;
    }
}
