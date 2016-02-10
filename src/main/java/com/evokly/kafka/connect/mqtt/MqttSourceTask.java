/**
 * Copyright 2016 Evokly S.A.
 * <p>See LICENSE file for License</p>
 **/

package com.evokly.kafka.connect.mqtt;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import com.evokly.kafka.connect.mqtt.util.Version;

/**
 * MqttSourceTask is a Kafka Connect SourceTask implementation that reads
 * from MQTT and generates Kafka Connect records.
 */
public class MqttSourceTask extends SourceTask implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);

    MqttClient client;
    String mqttClientId = MqttClient.generateClientId();

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        // Setup MQTT Client
        MqttConnectOptions connectOptions = new MqttConnectOptions();

        connectOptions.setCleanSession(true);
        connectOptions.setKeepAliveInterval(30);
//        connectOptions.setServerURIs(props.get(MqttSourceConstant.MQTT_BROKER_URLS).split(","));

        // Connect to Broker
        try {
            client = new MqttClient(props.get(MqttSourceConstant.MQTT_BROKER_URLS), mqttClientId, new MemoryPersistence());
            client.setCallback(this);
            client.connect(connectOptions);

            log.info("[{}] Connected to Broker", mqttClientId);
        } catch (MqttException e) {
            log.error("[{}] Connection to Broker failed!", mqttClientId);
        }

        // Setup topic
        try {
            String topic = props.get(MqttSourceConstant.MQTT_TOPIC);
            Integer qos = Integer.parseInt(props.get(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE));

            client.subscribe(topic, qos);

            log.info("MQTT subscribe to " + topic + " with QoS " + qos.toString());
        } catch (MqttException e) {
            log.error("MQTT subscribe failed! " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            client.disconnect();

            log.info("MQTT disconnected.");
        } catch (MqttException e) {
            log.info("MQTT disconnecting failed! " + e.getMessage());
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return null;
    }

    /**
     * Connection Lost
     * This callback is invoked upon losing the MQTT connection.
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.info("MQTT connection lost!");
    }

    /**
     * Delivery Complete
     * This callback is invoked when a message published by this client
     * is successfully received by the broker.
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Nothing to implement.
    }

    /**
     * Message Arrived
     * This callback is invoked when a message is received on a subscribed topic.
     *
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("-------------------------------------------------");
        log.info("| Topic:" + topic);
        log.info("| Message: " + new String(message.getPayload()));
        log.info("-------------------------------------------------");
    }
}
