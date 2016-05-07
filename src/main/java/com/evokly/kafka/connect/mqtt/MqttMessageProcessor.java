package com.evokly.kafka.connect.mqtt;

import org.apache.kafka.connect.data.Schema;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public interface MqttMessageProcessor {

    Schema getTopicSchema();

    Object getTopic();

    Schema getMessageSchema();

    Object getMessage();

    MqttMessageProcessor process(String topic, MqttMessage message);
}
