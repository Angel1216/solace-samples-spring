package com.solace.samples.spring.scs.interfaces;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Barista {
	
	@Input("input")
    SubscribableChannel orders();

    @Output("output")
    MessageChannel hotDrinks();

}
