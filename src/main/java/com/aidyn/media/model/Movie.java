package com.aidyn.media.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "movie-sequence-generator")
    @GenericGenerator(name = "movie-sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
	    @Parameter(name = "sequence_name", value = "movie_sequence"),
	    @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
    private Integer id;

    private String movieName;
    private String rating;
    private String posterImage;
    private String backDropImage;
    private String moviePath;
    @Lob
    private String description;
    private String dbMovieId;

    @ManyToMany
    private Set<Genre> genre;

    @Override
    public boolean equals(final Object obj) {
	return obj instanceof Movie && Objects.equals(id, ((Movie) obj).id);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public String toString() {
	return "Movie || { id: " + id + " dbMovieId: " + dbMovieId + " movieName: " + movieName + " rating: " + rating
		+ " posterImage: " + posterImage + " backDropImage: " + backDropImage + " moviePath: " + moviePath
		+ " description: " + description + " genre: " + genre + " }";

    }
}
