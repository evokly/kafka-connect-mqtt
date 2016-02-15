# Mqtt to Apache Kafka Connect [![Build Status](https://travis-ci.org/evokly/kafka-connect-mqtt.svg?branch=master)](https://travis-ci.org/evokly/kafka-connect-mqtt)

## Prerequisites
* [Apache Kafka](https://kafka.apache.org/) (0.9.x version) is publish-subscribe messaging rethought as a distributed commit log. 

## Usage
**For development:**

* run project:
`./gradlew clean jar && CLASSPATH=$CLASSPATH:build/libs/* connect-standalone.sh /usr/local/etc/kafka/connect-standalone.properties config/mqtt.properties`

**For production:**

* generate API documentation: `./gradlew javadoc` - output `./build/docs/javadoc`

## License
See [LICENSE](LICENSE) file for License