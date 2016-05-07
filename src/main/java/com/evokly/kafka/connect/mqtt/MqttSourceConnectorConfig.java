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

        Integer index = 0;
        Integer size = Integer.valueOf(properties.get(MqttSourceConstant.CONNECTIONS));

        for (int i = 0; i < size; i++) {
            Map<String, String> config = new HashMap<>();
            mConfigs.add(i, config);

            processProperty(i, MqttSourceConstant.KAFKA_TOPIC);
            processProperty(i, MqttSourceConstant.MQTT_CLIENT_ID);
            processProperty(i, MqttSourceConstant.MQTT_CLEAN_SESSION);
            processProperty(i, MqttSourceConstant.MQTT_CONNECTION_TIMEOUT);
            processProperty(i, MqttSourceConstant.MQTT_KEEP_ALIVE_INTERVAL);
            processProperty(i, MqttSourceConstant.MQTT_SERVER_URIS);
            processProperty(i, MqttSourceConstant.MQTT_TOPIC);
            processProperty(i, MqttSourceConstant.MQTT_QUALITY_OF_SERVICE);
            processProperty(i, MqttSourceConstant.MQTT_SSL_CA_CERT);
            processProperty(i, MqttSourceConstant.MQTT_SSL_CERT);
            processProperty(i, MqttSourceConstant.MQTT_SSL_PRIV_KEY);
        }

        if (log.isDebugEnabled()) {
            for (Map<String, String> configs : mConfigs) {
                log.debug(" Connection #{}:", index++);
                for (Map.Entry<String, String> config : configs.entrySet()) {
                    log.debug(" * {}={}", config.getKey(), config.getValue());
                }
            }
        }
    }

    /**
     * Process property and save to configuration.
     *
     * @param index connection number
     * @param key   field name
     */
    private void processProperty(Integer index, String key) {
        log.debug("Process property {}[{}]", key, index);

        String value = mProperties.get(MqttSourceConstant.PREFIX.replace("{}",
                String.valueOf(index)) + key);
        if (value != null) {
            mConfigs.get(index).put(key, value);
        }
    }

    /**
     * Getter for configuration size.
     *
     * @return number of processed configurations
     */
    public Integer size() {
        log.debug("Get number of configurations.");

        return mConfigs.size();
    }

    /**
     * Getter for configuration properties.
     *
     * @param index configuration index
     *
     * @return configuration properties
     */
    public Map<String, String> getProperties(Integer index) {
        log.debug("Get connection[{}] properties.", index);

        return mConfigs.get(index);
    }
}
