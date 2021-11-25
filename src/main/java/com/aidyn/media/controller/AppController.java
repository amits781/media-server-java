package com.aidyn.media.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aidyn.media.dto.MovieDTO;
import com.aidyn.media.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class AppController {

    @Autowired
    MovieService service;

    @GetMapping("/movies")
    public Set<MovieDTO> getAllMovies(@RequestParam Boolean exp) throws JsonMappingException, JsonProcessingException {
	if (exp) {
	    log.info("Requested all movies");
	} else {
	    log.info("Requested filtered movies");
	}
	return service.getAllMovies(exp);
    }

    @GetMapping("/movies/{id}")
    public MovieDTO getAllMovieById(@PathVariable Integer id) throws JsonMappingException, JsonProcessingException {
	log.info("Requested movie for Id:" + id);
	return service.getMovieById(id);
    }
}
