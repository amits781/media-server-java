package com.aidyn.media.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.aidyn.media.model.Genre;
import com.aidyn.media.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateUtil {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    @Value("${moviedb.api.url}")
    private String baseUrl;

    @Value("${moviedb.apikey}")
    private String apiKey;

    private final String searchMovieListEndpoint = "/search/movie";
    private final String getMovieDetailsEndpoint = "/movie";

    public String getMovieDbIdForName(String name) throws JsonMappingException, JsonProcessingException {
	String resourceUrl = baseUrl + searchMovieListEndpoint + addAuth() + addQueryParam("query", name);
	log.info("URL to search movie by name:" + resourceUrl);
	ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
	JsonNode root = mapper.readTree(response.getBody());
	JsonNode resultsMovieId = root.path("results").get(0).path("id");
	log.info("Id found for:{} is {}", name, resultsMovieId.asText());
	return resultsMovieId.asText();
    }

    public String getMovieDetailsById(String id) throws JsonMappingException, JsonProcessingException {
	String resourceUrl = baseUrl + getMovieDetailsEndpoint + "/" + id + addAuth();
	log.info("URL to search movie by id:" + resourceUrl);
	ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
	return response.getBody();
    }

    public Movie getMovieFromJson(String json) throws JsonMappingException, JsonProcessingException {
	JsonNode root = mapper.readTree(json);
	String dbMovieId = root.path("id").asText();
	String movieName = root.path("title").asText();
	String description = root.path("overview").asText();
	String rating = root.path("vote_average").asText();
	String posterImage = root.path("poster_path").asText();
	String backDropImage = root.path("backdrop_path").asText();
	return new Movie(null, movieName, rating, posterImage, backDropImage, backDropImage, description, dbMovieId,
		null);
    }

    public Set<Genre> getGenreFromMovieJson(String json) throws JsonMappingException, JsonProcessingException {
	JsonNode root = mapper.readTree(json);
	TypeReference<Set<Genre>> mapType = new TypeReference<Set<Genre>>() {
	};
	return mapper.readValue(mapper.writeValueAsString(root.path("genres")), mapType);
    }

    private String addAuth() {
	return "?api_key=" + apiKey;
    }

    private String addQueryParam(String key, String value) {
	return "&" + key + "=" + value;
    }
}
