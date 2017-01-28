/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MqttSourceConnectorTest {
    private MqttSourceConnector mConnector;
    Map<String, String> mSourceProperties;

    /**
     * Several tests need similar objects created before they can run.
     */
    @Before
    public void beforeEach() {
        mConnector = new MqttSourceConnector();

        mSourceProperties = new HashMap<>();

        mSourceProperties.put(MqttSourceConstant.KAFKA_TOPIC, "kafka_topic");

        mSourceProperties.put(MqttSourceConstant.MQTT_CLEAN_SESSION, "true");
        mSourceProperties.put(MqttSourceConstant.MQTT_CLIENT_ID, "TesetClientId");
        mSourceProperties.put(MqttSourceConstant.MQTT_CONNECTION_TIMEOUT, "15");
        mSourceProperties.put(MqttSourceConstant.MQTT_KEEP_ALIVE_INTERVAL, "30");
        mSourceProperties.put(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE, "2");
        mSourceProperties.put(MqttSourceConstant.MQTT_SERVER_URIS, "tcp://127.0.0.1:1883");
        mSourceProperties.put(MqttSourceConstant.MQTT_TOPIC, "mqtt_topic");
        mSourceProperties.put(MqttSourceConstant.MQTT_AUTO_RECONNECT, "false");
    }

    @Test
    public void testTaskClass() {
        assertEquals(MqttSourceTask.class, mConnector.taskClass());
    }

    @Test
    public void testSourceTasks() {
        mConnector.start(mSourceProperties);
        List<Map<String, String>> taskConfigs = mConnector.taskConfigs(1);

        assertEquals(taskConfigs.size(), 1);

        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.KAFKA_TOPIC), "kafka_topic");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_CLEAN_SESSION), "true");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_CLIENT_ID), "TesetClientId");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_CONNECTION_TIMEOUT), "15");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_KEEP_ALIVE_INTERVAL), "30");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE), "2");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_SERVER_URIS),
                "tcp://127.0.0.1:1883");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_TOPIC), "mqtt_topic");
        assertEquals(taskConfigs.get(0).get(MqttSourceConstant.MQTT_AUTO_RECONNECT), "false");
    }

}
