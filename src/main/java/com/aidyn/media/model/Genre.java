package com.aidyn.media.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name;

//    @ManyToMany
//    @JsonIgnore
//    private Set<Movie> movie;

    @Override
    public boolean equals(final Object obj) {
	return obj instanceof Genre && Objects.equals(id, ((Genre) obj).id);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public String toString() {
	return "Genre || { id: " + id + " name: " + name + " }";

    }
}
