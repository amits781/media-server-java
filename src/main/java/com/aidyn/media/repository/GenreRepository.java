package com.aidyn.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aidyn.media.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
