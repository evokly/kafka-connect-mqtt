/**
 * <p>Copyright 2016 Evokly S.A.
 * <p>See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.util.Version;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MqttSourceConnector is a Kafka Connect SourceConnector implementation that generates
 * tasks to ingest mqtt messages.
 **/
public class MqttSourceConnector extends SourceConnector {
    MqttSourceConnectorConfig config;

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        config = new MqttSourceConnectorConfig(props);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return MqttSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();

        for (int i = 0; i < config.size(); i++) {
            HashMap<String, String> properties = new HashMap();
            properties.put(MqttSourceConstant.KAFKA_TOPIC, config.getProperty(i, MqttSourceConstant.KAFKA_TOPIC));
            properties.put(MqttSourceConstant.MQTT_BROKER_URLS, config.getProperty(i, MqttSourceConstant.MQTT_BROKER_URLS));
            properties.put(MqttSourceConstant.MQTT_TOPIC, config.getProperty(i, MqttSourceConstant.MQTT_TOPIC));
            properties.put(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE, config.getProperty(i, MqttSourceConstant.MQTT_QUALITY_OF_SERVICE));
            configs.add(properties);
        }

        return configs;
    }

    @Override
    public void stop() {
        // Nothing to do since MqttSourceConnector has no background monitoring.
    }
}
