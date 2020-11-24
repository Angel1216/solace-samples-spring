package com.solace.samples.spring.scs.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;

import com.solace.samples.spring.common.SensorReading;

@EnableBinding(Barista.class)
public class CafeConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SendingBean.class);
	
	private Barista barista;
	
	@Autowired
	public CafeConfiguration(Barista barista) {
		this.barista = barista;
	}
	
	@Bean
	public void sensorReading() {
		
		SensorReading sensorReading = new SensorReading();
		
		sensorReading.setTemperature(44.22);
		sensorReading.setBaseUnit(SensorReading.BaseUnit.CELSIUS);
		
		barista.hotDrinks().send(MessageBuilder.withPayload(sensorReading).build());
		
		log.info("Sending: " + sensorReading);
	}
	
}
