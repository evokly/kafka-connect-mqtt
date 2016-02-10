/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MqttSourceConnectorConfig is responsible for correct configuration management.
 */
public class MqttSourceConnectorConfig {
    private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);

    List<Map<String, String>> mConfigs = new ArrayList<>();
    Map<String, String> mProperties;

    /**
     * Transform process properties.
     *
     * @param properties associative array with properties to be process
     */
    public MqttSourceConnectorConfig(Map<String, String> properties) {
        log.info("Initialize transform process properties");

        mProperties = properties;

        Integer size = Integer.valueOf(properties.get(MqttSourceConstant.CONNECTIONS));

        for (int i = 0; i < size; i++) {
            Map<String, String> config = new HashMap<>();
            mConfigs.add(i, config);

            processProperty(i, MqttSourceConstant.KAFKA_TOPIC);
            processProperty(i, MqttSourceConstant.MQTT_BROKER_URLS);
            processProperty(i, MqttSourceConstant.MQTT_TOPIC);
            processProperty(i, MqttSourceConstant.MQTT_QUALITY_OF_SERVICE);
        }
    }

    /**
     * Process property and save to configuration.
     *
     * @param index connection number
     * @param key   field name
     */
    private void processProperty(Integer index, String key) {
        log.info("Process property {}[{}]", key, index);

        mConfigs.get(index).put(key, mProperties.get(
                MqttSourceConstant.PREFIX.replace("{}", String.valueOf(index)) + key
        ));
    }

    /**
     * Getter for configuration size.
     *
     * @return number of processed configurations
     */
    public Integer size() {
        log.info("Get number of configurations.");

        return mConfigs.size();
    }

    /**
     * Getter for configuration property.
     *
     * @param index connection index
     * @param key   field name
     *
     * @return configuration value
     */
    public String getProperty(Integer index, String key) {
        log.info("Get property {}[{}]", key, index);

        return mConfigs.get(index).get(key);
    }
}
