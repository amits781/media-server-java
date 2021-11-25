package com.aidyn.media.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aidyn.media.dao.GenreDao;
import com.aidyn.media.dto.GenreDTO;
import com.aidyn.media.model.Genre;
import com.aidyn.media.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenreService {

    @Autowired
    GenreDao dao;

    @Autowired
    private ObjectMapper mapper;

    public void saveGenreList(Movie movie) {

	movie.getGenre().stream().forEach(gen -> {
	    if (!isGenrePresent(gen.getId())) {
		saveGenre(gen);
	    }
	});
    }

    public boolean isGenrePresent(Integer id) {
	if (dao.existById(id)) {
	    log.info("Genre for id{} found", id);
	    return true;
	}
	log.info("Genre for id{} not found", id);
	return false;
    }

    public Genre getGenreById(Integer id) {
	return dao.getById(id);
    }

    public Genre saveGenre(Genre genre) {
	log.info("Saving genre:" + genre);
	return dao.save(genre);
    }

    public Set<GenreDTO> getAllGenre() throws JsonMappingException, JsonProcessingException {
	TypeReference<Set<GenreDTO>> mapType = new TypeReference<Set<GenreDTO>>() {
	};
	return mapper.readValue(mapper.writeValueAsString(dao.getAll()), mapType);
//	return dao.getAll();
    }
}
