# Mqtt to Apache Kafka Connect [![Build Status](https://travis-ci.org/evokly/kafka-connect-mqtt.svg?branch=master)](https://travis-ci.org/evokly/kafka-connect-mqtt) [ ![Download](https://api.bintray.com/packages/evokly/maven/kafka-connect-mqtt/images/download.svg) ](https://bintray.com/evokly/maven/kafka-connect-mqtt/_latestVersion)

## Prerequisites
* [Apache Kafka](https://kafka.apache.org/) (0.10.x version) is publish-subscribe messaging rethought as a distributed commit log.

## Usage
**For development:**

* run check (checkstyle, findbugs, test):  
  `./gradlew clean check`

* run project:  
  `connect-standalone.sh /usr/local/etc/kafka/connect-standalone.properties config/mqtt.properties`
    * libs needs to be added to CLASSPATH:
        * `kafka-connect-mqtt-{project.version}.jar`
        * `org.eclipse.paho.client.mqttv3-1.0.2.jar`
        * if used with ssl there are more.. (`./gradlew copyRuntimeLibs` copies all runtime libs to `./build/output/lib`)

**For production:**

* build project: `./gradlew clean jar` - output `./build/libs`

* generate API documentation: `./gradlew javadoc` - output `./build/docs/javadoc`

## License
See [LICENSE](LICENSE) file for License