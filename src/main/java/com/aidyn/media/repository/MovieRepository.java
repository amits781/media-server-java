package com.aidyn.media.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aidyn.media.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    public Optional<Movie> findByDbMovieId(String dbMovieId);

    public List<Movie> findByGenreName(String name);

}
