/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License</p>
 **/

package com.evokly.kafka.connect.mqtt;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.util.List;
import java.util.Map;

/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public class MqttSourceTask extends SourceTask {
    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> props) {

    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return null;
    }

    @Override
    public void stop() {

    }
}
