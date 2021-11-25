package com.aidyn.media.controller;

import java.net.UnknownHostException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aidyn.media.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitController {

    @Autowired
    MovieService service;

    @RabbitListener(queues = "movies.queue")
    public void listen(String movieName) throws JsonMappingException, JsonProcessingException, UnknownHostException {
	log.info("Processing Started for : " + movieName);
	try {
	    service.findMovieByName(movieName);
	} catch (Exception e) {
	    log.error(e.getLocalizedMessage());
	    e.printStackTrace();
	}
    }
}
