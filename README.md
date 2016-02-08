# Mqtt to Apache Kafka Connect

## Prerequisites
* [Apache Kafka](https://kafka.apache.org/) (0.9.x version) is publish-subscribe messaging rethought as a distributed commit log. 

## Usage
**For development:**

* run project:
`./gradlew clean jar && CLASSPATH=$CLASSPATH:build/libs/* connect-standalone.sh /usr/local/etc/kafka/connect-standalone.properties config/mqtt.properties`

**For production:**

* generate API documentation: `./gradlew javadoc` - output `./build/docs/javadoc`

## License
Copyright 2016 Evokly S.A., all rights reserved.