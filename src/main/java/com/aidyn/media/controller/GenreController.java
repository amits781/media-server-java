package com.aidyn.media.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aidyn.media.dto.GenreDTO;
import com.aidyn.media.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public Set<GenreDTO> getAllGenres() throws JsonMappingException, JsonProcessingException {
	log.info("All genre requested");
	return genreService.getAllGenre();
    }
}
