/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License</p>
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.util.Version;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.List;
import java.util.Map;


/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public class MqttSourceConnector extends SourceConnector {
    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {

    }

    @Override
    public Class<? extends Task> taskClass() {
        return MqttSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        return null;
    }

    @Override
    public void stop() {

    }
}
