package com.evokly.kafka.connect.mqtt;

import java.util.*;

public class MqttSourceConnectorConfig {
    List<Map<String, String>> configs = new ArrayList<>();
    Map<String, String> properties;

    public MqttSourceConnectorConfig(Map<String, String> props) {
        properties = props;
        // @TODO make any validation / configuration checking
        Integer taskSize = Integer.valueOf(props.get(MqttSourceConstant.CONNECTIONS));

        for (int i = 0; i < taskSize; i++) {
            Map<String, String> config = new HashMap<>();

            config.put(MqttSourceConstant.KAFKA_TOPIC, getProp(i, MqttSourceConstant.KAFKA_TOPIC));
            config.put(MqttSourceConstant.MQTT_BROKER_URLS, getProp(i, MqttSourceConstant.MQTT_BROKER_URLS));
            config.put(MqttSourceConstant.MQTT_TOPIC, getProp(i, MqttSourceConstant.MQTT_TOPIC));
            config.put(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE, getProp(i, MqttSourceConstant.MQTT_QUALITY_OF_SERVICE));

            configs.add(i, config);
        }
    }

    private String getProp(Integer i, String key) {
        return properties.get(MqttSourceConstant.PREFIX.replace("{}", String.valueOf(i)) + key);
    }

    public Integer size() {
        return configs.size();
    }

    public String getProperty(Integer i, String key) {
        return configs.get(i).get(key);
    }
}
