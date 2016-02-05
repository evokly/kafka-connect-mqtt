package com.evokly.kafka.connect.mqtt;
/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License</p>
 **/

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.List;
import java.util.Map;


public class MqttSourceConnector extends SourceConnector {
    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> props) {

    }

    @Override
    public Class<? extends Task> taskClass() {
        return null;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        return null;
    }

    @Override
    public void stop() {

    }
}
