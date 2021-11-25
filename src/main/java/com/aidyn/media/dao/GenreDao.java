package com.aidyn.media.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aidyn.media.model.Genre;
import com.aidyn.media.repository.GenreRepository;

@Repository
public class GenreDao {
    @Autowired
    GenreRepository repo;

    public List<Genre> getAll() {
	return repo.findAll();
    }

    public Genre getById(Integer id) {
	return repo.findById(id).get();
    }

    public boolean existById(Integer id) {
	return repo.existsById(id);
    }

    public Genre save(Genre genre) {
	return repo.save(genre);
    }

}
