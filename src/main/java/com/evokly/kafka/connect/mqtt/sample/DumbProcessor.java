package com.evokly.kafka.connect.mqtt.sample;

import com.evokly.kafka.connect.mqtt.MqttMessageProcessor;
import org.apache.kafka.connect.data.Schema;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public class DumbProcessor implements MqttMessageProcessor {
    private static final Logger log = LoggerFactory.getLogger(DumbProcessor.class);
    private MqttMessage mMessage;
    private Object mTopic;

    @Override
    public Schema getTopicSchema() {
        log.debug("returning topic schema");
        return Schema.STRING_SCHEMA;
    }

    @Override
    public Object getTopic() {
        log.debug("returning topic in form {}", mTopic);
        return mTopic;
    }

    @Override
    public Schema getMessageSchema() {
        log.debug("returning message schema");
        return Schema.BYTES_SCHEMA;
    }

    @Override
    public Object getMessage() {
        log.debug("returning message {}", mMessage.getPayload());
        return mMessage.getPayload();
    }

    @Override
    public MqttMessageProcessor process(String topic, MqttMessage message) {
        this.mTopic = topic;
        this.mMessage = message;
        return this;
    }
}
