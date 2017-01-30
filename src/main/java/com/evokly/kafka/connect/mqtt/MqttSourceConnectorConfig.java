/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.sample.DumbProcessor;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * MqttSourceConnectorConfig is responsible for correct configuration management.
 */
public class MqttSourceConnectorConfig extends AbstractConfig {
    private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);
    private static final ConfigDef.Recommender MODE_SSL_RECOMMENDER =  new SslRecommender();

    /**
     * Create default mConfig.
     * @return default mConfig
     */
    public static ConfigDef baseConfigDef() {
        return new ConfigDef()
                .define(MqttSourceConstant.KAFKA_TOPIC, Type.STRING, "mqtt", Importance.LOW,
                        "Kafka topic to put received data \n Depends on message processor")
                .define(MqttSourceConstant.MQTT_CLIENT_ID, Type.STRING, null, Importance.MEDIUM,
                        "mqtt client id to use don't set to use random")
                .define(MqttSourceConstant.MQTT_CLEAN_SESSION, Type.BOOLEAN, true, Importance.HIGH,
                        "use clean session in connection?")
                .define(MqttSourceConstant.MQTT_CONNECTION_TIMEOUT, Type.INT, 30, Importance.LOW,
                        "connection timeout to use")
                .define(MqttSourceConstant.MQTT_KEEP_ALIVE_INTERVAL, Type.INT, 60, Importance.LOW,
                        "keepalive interval to use")
                .define(MqttSourceConstant.MQTT_AUTO_RECONNECT, Type.BOOLEAN, false, Importance.LOW,
                        "flag if client should reconnect when connection is lost")
                .define(MqttSourceConstant.MQTT_SERVER_URIS, Type.STRING,
                        "tcp://localhost:1883", Importance.HIGH,
                        "mqtt server to connect to")
                .define(MqttSourceConstant.MQTT_TOPIC, Type.STRING, "#", Importance.HIGH,
                        "mqtt server to connect to")
                .define(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE, Type.INT, 1, Importance.LOW,
                        "mqtt qos to use")
                .define(MqttSourceConstant.MQTT_SSL_CA_CERT, Type.STRING, null, Importance.LOW,
                        "CA cert file to use if using ssl",
                        "SSL", 1, ConfigDef.Width.LONG, "CA cert", MODE_SSL_RECOMMENDER)
                .define(MqttSourceConstant.MQTT_SSL_CERT, Type.STRING, null, Importance.LOW,
                        "cert file to use if using ssl",
                        "SSL", 2, ConfigDef.Width.LONG, "Cert", MODE_SSL_RECOMMENDER)
                .define(MqttSourceConstant.MQTT_SSL_PRIV_KEY, Type.STRING, null, Importance.LOW,
                        "cert priv key to use if using ssl",
                        "SSL", 3, ConfigDef.Width.LONG, "Key", MODE_SSL_RECOMMENDER)
                .define(MqttSourceConstant.MQTT_USERNAME, Type.STRING, null, Importance.MEDIUM,
                        "username to authenticate to mqtt broker")
                .define(MqttSourceConstant.MQTT_PASSWORD, Type.STRING, null, Importance.MEDIUM,
                        "password to authenticate to mqtt broker")
                .define(MqttSourceConstant.MESSAGE_PROCESSOR, Type.CLASS,
                        DumbProcessor.class, Importance.HIGH,
                        "message processor to use");
    }

    static ConfigDef config = baseConfigDef();

    /**
     * Transform process properties.
     *
     * @param properties associative array with properties to be process
     */
    public MqttSourceConnectorConfig(Map<String, String> properties) {
        super(config, properties);
        log.info("Initialize transform process properties");
    }

    private static class SslRecommender implements ConfigDef.Recommender {

        @Override
        public List<Object> validValues(String name, Map<String, Object> parsedConfig) {
            return new LinkedList<>();
        }

        @Override
        public boolean visible(String name, Map<String, Object> parsedConfig) {
            String mode = (String) parsedConfig.get(MqttSourceConstant.MQTT_SERVER_URIS);
            return mode.startsWith("ssl://");
        }
    }

    public static void main(String[] args) {
        System.out.println(config.toRst());
    }
}
