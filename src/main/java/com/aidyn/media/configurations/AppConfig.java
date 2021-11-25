package com.aidyn.media.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate() {
	return new RestTemplate();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
	return new ObjectMapper();
    }

}
