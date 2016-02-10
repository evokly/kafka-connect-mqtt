/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.util.Version;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * MqttSourceTask is a Kafka Connect SourceTask implementation that reads
 * from MQTT and generates Kafka Connect records.
 */
public class MqttSourceTask extends SourceTask implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);

    MqttClient mClient;
    String mMqttClientId = MqttClient.generateClientId();

    /**
     * Get the version of this task. Usually this should be the same as the corresponding
     * {@link MqttSourceConnector} class's version.
     *
     * @return the version, formatted as a String
     */
    @Override
    public String version() {
        return Version.getVersion();
    }

    /**
     * Start the Task.
     *
     * @param props initial configuration
     */
    @Override
    public void start(Map<String, String> props) {
        // Setup MQTT Client
        MqttConnectOptions connectOptions = new MqttConnectOptions();

        connectOptions.setCleanSession(true);
        connectOptions.setKeepAliveInterval(30);
        // connectOptions.setServerURIs(props.get(MqttSourceConstant.MQTT_BROKER_URLS).split(","));

        // Connect to Broker
        try {
            mClient = new MqttClient(props.get(MqttSourceConstant.MQTT_BROKER_URLS), mMqttClientId,
                    new MemoryPersistence());
            mClient.setCallback(this);
            mClient.connect(connectOptions);

            log.info("[{}] Connected to Broker", mMqttClientId);
        } catch (MqttException e) {
            log.error("[{}] Connection to Broker failed!", mMqttClientId, e);
        }

        // Setup topic
        try {
            String topic = props.get(MqttSourceConstant.MQTT_TOPIC);
            Integer qos = Integer.parseInt(props.get(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE));

            mClient.subscribe(topic, qos);

            log.info("MQTT subscribe to " + topic + " with QoS " + qos.toString());
        } catch (MqttException e) {
            log.error("MQTT subscribe failed! " + e);
        }
    }

    /**
     * Stop this task.
     */
    @Override
    public void stop() {
        try {
            mClient.disconnect();

            log.info("MQTT disconnected.");
        } catch (MqttException e) {
            log.error("MQTT disconnecting failed!", e);
        }
    }

    /**
     * Poll this SourceTask for new records. This method should block if no data is currently
     * available.
     *
     * @return a list of source records
     *
     * @throws InterruptedException thread is waiting, sleeping, or otherwise occupied,
     *                              and the thread is interrupted, either before or during the
     *                              activity
     */
    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return null;
    }

    /**
     * This method is called when the connection to the server is lost.
     *
     * @param cause the reason behind the loss of connection.
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.info("MQTT connection lost!", cause);
    }

    /**
     * Called when delivery for a message has been completed, and all acknowledgments have been
     * received.
     *
     * @param token the delivery token associated with the message.
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Nothing to implement.
    }

    /**
     * This method is called when a message arrives from the server.
     *
     * @param topic   name of the topic on the message was published to
     * @param message the actual message.
     *
     * @throws Exception if a terminal error has occurred, and the client should be
     *                   shut down.
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("-------------------------------------------------");
        log.info("| Topic:" + topic);
        log.info("| Message: " + new String(message.getPayload(), "UTF-8"));
        log.info("-------------------------------------------------");
    }
}
