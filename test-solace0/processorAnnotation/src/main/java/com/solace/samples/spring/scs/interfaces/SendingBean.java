package com.solace.samples.spring.scs.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solace.samples.spring.common.SensorReading;

@Component
public class SendingBean {
	
	private static final Logger log = LoggerFactory.getLogger(SendingBean.class);
	private CafeConfiguration cafeConfiguration;
	
	@Autowired
	public SendingBean(CafeConfiguration cafeConfiguration) {
		this.cafeConfiguration = cafeConfiguration;
	}
	
	public void sensorReadingxxx() {
		SensorReading sensorReading = new SensorReading();
		
		sensorReading.setTemperature(44.22);
		sensorReading.setBaseUnit(SensorReading.BaseUnit.CELSIUS);
		
		
		
		log.info("Sending: " + sensorReading);
	}

}
