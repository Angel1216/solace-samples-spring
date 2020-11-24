package com.solace.samples.spring.scs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solace.samples.spring.scs.interfaces.CafeConfiguration;

@RestController
public class ControllerQ {
	
	private CafeConfiguration cafeConfiguration;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String sendMessage() {
		cafeConfiguration.sensorReading();
		return "Hello World!!";
	}

}
