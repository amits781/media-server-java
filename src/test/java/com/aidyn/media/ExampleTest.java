package com.aidyn.media;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aidyn.media.model.Genre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExampleTest {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
//	File currentDir = new File("F:\\Movies"); // current directory
//	displayDirectoryContents(currentDir);
//	String json = "{\"adult\":false,\"backdrop_path\":\"/a0xTB1vBxMGt6LGG4N7k1wO9lfL.jpg\",\"belongs_to_collection\":null,\"budget\":48000000,\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":35,\"name\":\"Comedy\"}],\"homepage\":\"https://www.charliesangels.movie/\",\"id\":458897,\"imdb_id\":\"tt5033998\",\"original_language\":\"en\",\"original_title\":\"Charlie's Angels\",\"overview\":\"When a systems engineer blows the whistle on a dangerous technology, Charlie's Angels from across the globe are called into action, putting their lives on the line to protect society.\",\"popularity\":28.327,\"poster_path\":\"/1DPUFG6QnGqzpvEaDEv7TaepycM.jpg\",\"production_companies\":[{\"id\":34,\"logo_path\":\"/GagSvqWlyPdkFHMfQ3pNq6ix9P.png\",\"name\":\"Sony Pictures\",\"origin_country\":\"US\"},{\"id\":5,\"logo_path\":\"/71BqEFAF4V3qjjMPCpLuyJFB9A.png\",\"name\":\"Columbia Pictures\",\"origin_country\":\"US\"},{\"id\":12365,\"logo_path\":null,\"name\":\"Brownstone Productions\",\"origin_country\":\"US\"},{\"id\":84792,\"logo_path\":null,\"name\":\"2.0 Entertainment\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"2019-11-14\",\"revenue\":73279888,\"runtime\":118,\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"Unseen. Undivided. Unstoppable.\",\"title\":\"Charlie's Angels\",\"video\":false,\"vote_average\":6.6,\"vote_count\":2226}";
//	getMovieFromJson(json);
//	getGenreFromJson(json);
//	String name = "http://192.168.0.109:8091/Charlie's Angels_DBMID-458897/Charlie's.Angels.2019.720p.BluRay.x264.AAC-[YTS.MX].mp4";
//	name = name.substring((name.indexOf("8091") + 5));
//	name = name.substring(0, name.indexOf("/"));
	String name = "_Exp_Caged Women_DBMID-91865";
	boolean isEpx = name.contains("_Exp_");
	System.out.println(isEpx);
    }

    public static void displayDirectoryContents(File dir) {
	Map<String, List<String>> movies = Arrays.asList(dir.listFiles()).stream()
		.collect(Collectors.groupingBy(f -> f.isDirectory() ? "Directory" : "File(Ignored)",
			Collectors.mapping(File::getName, Collectors.toList())));
	System.out.println(movies);
//	String json = "{\"adult\":false,\"backdrop_path\":\"/a0xTB1vBxMGt6LGG4N7k1wO9lfL.jpg\",\"belongs_to_collection\":null,\"budget\":48000000,\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":35,\"name\":\"Comedy\"}],\"homepage\":\"https://www.charliesangels.movie/\",\"id\":458897,\"imdb_id\":\"tt5033998\",\"original_language\":\"en\",\"original_title\":\"Charlie's Angels\",\"overview\":\"When a systems engineer blows the whistle on a dangerous technology, Charlie's Angels from across the globe are called into action, putting their lives on the line to protect society.\",\"popularity\":28.327,\"poster_path\":\"/1DPUFG6QnGqzpvEaDEv7TaepycM.jpg\",\"production_companies\":[{\"id\":34,\"logo_path\":\"/GagSvqWlyPdkFHMfQ3pNq6ix9P.png\",\"name\":\"Sony Pictures\",\"origin_country\":\"US\"},{\"id\":5,\"logo_path\":\"/71BqEFAF4V3qjjMPCpLuyJFB9A.png\",\"name\":\"Columbia Pictures\",\"origin_country\":\"US\"},{\"id\":12365,\"logo_path\":null,\"name\":\"Brownstone Productions\",\"origin_country\":\"US\"},{\"id\":84792,\"logo_path\":null,\"name\":\"2.0 Entertainment\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"2019-11-14\",\"revenue\":73279888,\"runtime\":118,\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"Unseen. Undivided. Unstoppable.\",\"title\":\"Charlie's Angels\",\"video\":false,\"vote_average\":6.6,\"vote_count\":2226}";
    }

    public static void getGenreFromJson(String json) throws JsonMappingException, JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	JsonNode root = mapper.readTree(json);
	TypeReference<List<Genre>> mapType = new TypeReference<List<Genre>>() {
	};
	List<Genre> jsonToPersonList = mapper.readValue(mapper.writeValueAsString(root.path("genres")), mapType);
	System.out.println(jsonToPersonList);
    }

}
