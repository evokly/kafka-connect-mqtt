/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import org.apache.kafka.connect.source.SourceRecord;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MqttSourceTaskTest {
    private MqttSourceTask mTask;
    private Map<String, String> mEmptyConfig = new HashMap<String, String>();

    /**
     * Several tests need similar objects created before they can run.
     */
    @Before
    public void beforeEach() {
        mTask = new MqttSourceTask();
        mTask.start(mEmptyConfig);
    }

    @Test
    public void testPoll() throws Exception {
        // empty queue
        assertEquals(mTask.mQueue.size(), 0);

        // add dummy message to queue
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload("test_message".getBytes(StandardCharsets.UTF_8));
        mTask.messageArrived("test_topic", mqttMessage);

        // check message to be process
        assertEquals(mTask.mQueue.size(), 1);

        // generate and validate SourceRecord
        List<SourceRecord> sourceRecords = mTask.poll();

        assertEquals(sourceRecords.size(), 1);
        assertEquals(sourceRecords.get(0).key(), "test_topic");
        assertEquals(new String((byte[]) sourceRecords.get(0).value(), "UTF-8"), "test_message");

        // empty queue
        assertEquals(mTask.mQueue.size(), 0);
    }
}
