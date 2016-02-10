/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.util.Version;

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MqttSourceConnector is a Kafka Connect SourceConnector implementation that generates tasks to
 * ingest mqtt messages.
 **/
public class MqttSourceConnector extends SourceConnector {
    private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);

    MqttSourceConnectorConfig mConfig;

    /**
     * Get the version of this connector.
     *
     * @return the version, formatted as a String
     */
    @Override
    public String version() {
        return Version.getVersion();
    }

    /**
     * Start this Connector. This method will only be called on a clean Connector, i.e. it has
     * either just been instantiated and initialized or {@link #stop()} has been invoked.
     *
     * @param props configuration settings
     */
    @Override
    public void start(Map<String, String> props) {
        log.info("Start a MqttSourceConnector");
        mConfig = new MqttSourceConnectorConfig(props);
    }

    /**
     * SourceTask implementation for this Connector.
     *
     * @return SourceTask class instance
     */
    @Override
    public Class<? extends Task> taskClass() {
        return MqttSourceTask.class;
    }

    /**
     * Returns a set of configurations for Tasks based on the current configuration,
     * producing at most count configurations.
     *
     * @param maxTasks maximum number of configurations to generate
     *
     * @return configurations for Tasks
     */
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();

        for (int i = 0; i < mConfig.size(); i++) {
            HashMap<String, String> properties = new HashMap<>();
            properties.put(MqttSourceConstant.KAFKA_TOPIC, mConfig.getProperty(i,
                    MqttSourceConstant.KAFKA_TOPIC));
            properties.put(MqttSourceConstant.MQTT_BROKER_URLS, mConfig.getProperty(i,
                    MqttSourceConstant.MQTT_BROKER_URLS));
            properties.put(MqttSourceConstant.MQTT_TOPIC, mConfig.getProperty(i,
                    MqttSourceConstant.MQTT_TOPIC));
            properties.put(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE, mConfig.getProperty(i,
                    MqttSourceConstant.MQTT_QUALITY_OF_SERVICE));
            configs.add(properties);
        }

        return configs;
    }

    /**
     * Stop this connector.
     */
    @Override
    public void stop() {
        log.info("Stop the MqttSourceConnector");
    }
}
