package com.in28minutes.microservices.limitsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.limitsservice.configuration.Configuration;
import com.in28minutes.microservices.limitsservice.source.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand; 

@RestController
@RefreshScope
public class LimitsConfigurationController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations(){
		
		
		logger.info("{}");
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
		
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveConfigurations(){
		throw new RuntimeException("Not availble!!!");
		
	}
	
	
	public LimitConfiguration fallbackRetrieveConfiguration(){
		return new LimitConfiguration(9999, 9);   // return default values 
		
	}

}
