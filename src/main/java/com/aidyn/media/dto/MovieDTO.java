package com.aidyn.media.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Integer id;
    private String movieName;
    private String rating;
    private String posterImage;
    private String backDropImage;
    private String moviePath;
    private String description;
    private String dbMovieId;
    private Set<GenreDTO> genre;
}
