/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MqttSourceTaskMessage is a container for mqtt message.
 */
public class MqttSourceTaskMessage {
    private String mTopic;
    private MqttMessage mMessage;

    public MqttSourceTaskMessage(String topic, MqttMessage message) {
        this.mTopic = topic;
        this.mMessage = message;
    }

    public String getTopic() {
        return mTopic;
    }

    public MqttMessage getMessage() {
        return mMessage;
    }
}
