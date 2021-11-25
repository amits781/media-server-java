package com.aidyn.media.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aidyn.media.model.Movie;
import com.aidyn.media.repository.MovieRepository;

@Repository
public class MovieDao {
    @Autowired
    MovieRepository repo;

    public List<Movie> getAll() {
	return repo.findAll();
    }

    public List<Movie> getAllExpMovie() {
	return repo.findByGenreName("Adult");
    }

    public Movie getById(Integer id) {
	return repo.findById(id).get();
    }

    public Movie save(Movie movie) {
	return repo.save(movie);
    }

    public Optional<Movie> isExistingMovie(String dbMovieId) {
	return repo.findByDbMovieId(dbMovieId);
    }

    public void deleteMovieById(Integer id) {
	repo.deleteById(id);
    }

}
