package com.evokly.kafka.connect.mqtt;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MqttSourceTaskReconnectTest {

	MqttSourceTask underTest;
	MqttSourceConnectorConfig configMock;

	@Before
	public void beforeEach() throws Exception {
		underTest = new MqttSourceTask();
		configMock = mock(MqttSourceConnectorConfig.class);
		underTest.mConfig = configMock;
	}

	@Test
	public void test_reconnect_is_called() throws Exception {
		doReturn(true).when(configMock).getBoolean(MqttSourceConstant.MQTT_AUTO_RECONNECT);

		final MqttSourceTask spy = spy(underTest);

		spy.connectionLost(mock(Throwable.class));

		verify(spy, times(1)).reconnectToBroker();
	}

	@Test
	public void test_that_no_reconnect_if_option_isnt_set() throws Exception {
		doReturn(false).when(configMock).getBoolean(MqttSourceConstant.MQTT_AUTO_RECONNECT);

		final MqttSourceTask spy = spy(underTest);

		spy.connectionLost(mock(Throwable.class));

		verify(spy, times(0)).reconnectToBroker();
	}

}