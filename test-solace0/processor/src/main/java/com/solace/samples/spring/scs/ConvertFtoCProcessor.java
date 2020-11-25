/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.solace.samples.spring.scs;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.solace.samples.spring.common.SensorReading;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@SpringBootApplication
public class ConvertFtoCProcessor {

	public static void main(String[] args) {
		SpringApplication.run(ConvertFtoCProcessor.class, args);
	}

//	@Bean
//	public Function<SensorReading, SensorReading> convertFtoC() {
//		return reading -> {
//			log.info("Received: " + reading);
//
//			double temperatureCelsius = (reading.getTemperature().doubleValue() - 32) * 5 / 9;
//			reading.setTemperature(temperatureCelsius);
//			reading.setBaseUnit(SensorReading.BaseUnit.CELSIUS);
//
//			log.info("Sending: " + reading);
//
//			return reading;
//		};
//	}
	
	@Bean
	public static Function<Flux<SensorReading>, Tuple2<Flux<SensorReading>, Flux<SensorReading>>> convertFtoC() {
		return flux -> {
			
			Flux<SensorReading> connectedFlux = flux.publish().autoConnect(2);
			
			connectedFlux.log().map(sensor -> {
				double temperatureCelsius = (sensor.getTemperature().doubleValue() - 32) * 5 / 9;
				sensor.setTemperature(temperatureCelsius);
				sensor.setBaseUnit(SensorReading.BaseUnit.CELSIUS);
				return sensor;
			}).log().subscribe();
			
			UnicastProcessor<SensorReading> even = UnicastProcessor.create();
			UnicastProcessor<SensorReading> odd = UnicastProcessor.create();
			Flux<SensorReading> evenFlux = connectedFlux.filter(number -> number.getTemperature() > 0).doOnNext(number -> even.onNext(number));
			Flux<SensorReading> oddFlux = connectedFlux.filter(number -> number.getTemperature() < 0).doOnNext(number -> odd.onNext(number));

			return Tuples.of(Flux.from(even).doOnSubscribe(x -> evenFlux.subscribe()), Flux.from(odd).doOnSubscribe(x -> oddFlux.subscribe()));
		};
	}
}
