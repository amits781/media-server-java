package com.aidyn.media.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aidyn.media.exceptions.MovieParsingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileParser {

    @Value("${movie.path}")
    private String movieBasePath;

    private File movieDirectory;

    private List<String> extentions = Arrays.asList(new String[] { "mp4", "m4v", "mpeg", "webm" });

    public Map<String, List<String>> parseMovies() {
	movieDirectory = new File(movieBasePath);
	Map<String, List<String>> movies = Arrays.asList(movieDirectory.listFiles()).stream()
		.collect(Collectors.groupingBy(f -> f.isDirectory() ? "Directory" : "File(Ignored)",
			Collectors.mapping(File::getName, Collectors.toList())));
	return movies;
    }

    public String getMovieFileInDirectory(String directory) {
	log.info("Searching for movie in: " + movieBasePath + "/" + directory);
	movieDirectory = new File(movieBasePath + "/" + directory);
	Optional<File> movieFile = Arrays.asList(movieDirectory.listFiles()).stream().filter(m -> {
	    if (getFileExtension(m.getName()).isPresent()) {
		String fileExt = getFileExtension(m.getName()).get();
		fileExt = fileExt.toLowerCase();
		log.info("File found: {} has extention {}", m.getName(), fileExt);
		return extentions.contains(fileExt);
	    }
	    return false;
	}).findFirst();
	if (movieFile.isEmpty())
	    throw new MovieParsingException("Movie not present in Directory:" + directory);
	return movieFile.get().getName();
    }

    public Optional<String> getFileExtension(String filename) {
	return Optional.ofNullable(filename).filter(f -> f.contains("."))
		.map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
