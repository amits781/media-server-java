package com.aidyn.media.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aidyn.media.service.MovieService;
import com.aidyn.media.utils.FileParser;
import com.aidyn.media.utils.RabbitMQSender;

@RestController
public class SchedulerController {

    @Autowired
    FileParser parser;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    MovieService movieService;

    @GetMapping("/process")
    public Map<String, List<String>> processLocalMovies() {
	Map<String, List<String>> moviesFound = parser.parseMovies();
	List<String> moviesToProcess = moviesFound.get("Directory");
	movieService.syncFolder(moviesToProcess);
	moviesToProcess.stream().forEach(movie -> rabbitMQSender.send(movie));
	return moviesFound;
    }

}
