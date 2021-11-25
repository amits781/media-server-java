package com.aidyn.media.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aidyn.media.dao.MovieDao;
import com.aidyn.media.dto.MovieDTO;
import com.aidyn.media.model.Genre;
import com.aidyn.media.model.Movie;
import com.aidyn.media.utils.FileParser;
import com.aidyn.media.utils.RestTemplateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private RestTemplateUtil restTemplate;

    @Value("${file.server.port}")
    private String fileServerPort;

    @Autowired
    private FileParser fileParser;

    @Autowired
    private MovieDao dao;

    @Autowired
    private GenreService genService;

    @Autowired
    private ObjectMapper mapper;

    public void findMovieByName(String dirName)
	    throws JsonMappingException, JsonProcessingException, UnknownHostException {
//	String dbMovieId = restTemplate.getMovieDbIdForName(dirName);
	String dbMovieId = dirName.substring(dirName.indexOf("DBMID-") + 6);
	String movieDetails = restTemplate.getMovieDetailsById(dbMovieId);
	Movie movie = restTemplate.getMovieFromJson(movieDetails);
	String movieFileName = fileParser.getMovieFileInDirectory(dirName);
	String localHost = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + fileServerPort;
	String pathToFile = localHost + "/" + dirName + "/" + movieFileName;
	movie.setMoviePath(pathToFile);
	movie.setGenre(restTemplate.getGenreFromMovieJson(movieDetails));
	genService.saveGenreList(movie);
	checkExpMovie(movie, dirName);
	Optional<Movie> existingMovie = dao.isExistingMovie(dbMovieId);
	if (existingMovie.isPresent()) {
	    movie.setId(existingMovie.get().getId());
	    log.info("Updating movie entry:" + movie);
	} else {
	    log.info("New movie entry:" + movie);
	}
	dao.save(movie);
    }

    private void checkExpMovie(Movie movie, String dirName) {
	if (dirName.contains("_Exp_")) {
	    Genre expGen = genService.getGenreById(0);
	    Set<Genre> genres = movie.getGenre();
	    genres.add(expGen);
	    movie.setGenre(genres);
	}

    }

    public Set<MovieDTO> getAllMovies(Boolean exp) throws JsonMappingException, JsonProcessingException {
	List<Movie> allMovies = dao.getAll();
	List<Movie> expMovies = dao.getAllExpMovie();
	if (!exp) {
	    allMovies.removeAll(expMovies);
	    log.info("filtered out {} movies", expMovies.size());
	} else {
	    log.info("filtered out {} movies", allMovies.size());
	    allMovies = expMovies;
	}
	TypeReference<Set<MovieDTO>> mapType = new TypeReference<Set<MovieDTO>>() {
	};
	return mapper.readValue(mapper.writeValueAsString(allMovies), mapType);
    }

    public MovieDTO getMovieById(Integer id) throws JsonMappingException, JsonProcessingException {
	return mapper.readValue(mapper.writeValueAsString(dao.getById(id)), MovieDTO.class);
//	return dao.getById(id);
    }

    public void syncFolder(List<String> movieDirs) {
	List<Movie> allMovies = dao.getAll();
	allMovies.forEach(movie -> {
	    String path = movie.getMoviePath();
	    path = path.substring((path.indexOf(fileServerPort) + 5));
	    String dir = path.substring(0, path.indexOf("/"));
	    if (!movieDirs.contains(dir)) {
		log.info("Directory {} no longer exist.....Deleting", dir);
		dao.deleteMovieById(movie.getId());
	    }
	});
    }
}
