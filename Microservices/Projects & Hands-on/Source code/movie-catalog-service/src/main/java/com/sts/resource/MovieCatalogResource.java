package com.sts.resource;


import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.Extension.Parameter;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sts.models.CatalogItem;
import com.sts.models.Movie;
import com.sts.models.Rating;
import com.sts.models.UserRating;
import com.sts.services.Services;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Services services;
	
	//using resttempelate
	@GetMapping("/{userId}")
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog") //circuit breaking, fall back to method when failure
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		
		//Movie movie=restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
		
		UserRating ratings = services.getUserRating(userId);
		
		
		return ratings.getUserRating().stream().map(rating ->
			//getting movie details for each movie id
				services.getCatalogItem(rating)).
				collect(Collectors.toList());
		
		
	//return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));
	
	}
	
	public List<CatalogItem> getFallbackCatalog(@PathVariable String userId){
		return Arrays.asList(new CatalogItem("No", "", 0));
	}

	/*
	//using reactive webclient
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		//RestTemplate restTemplate=new RestTemplate();
		//Movie movie=restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
		WebClient.Builder builder=WebClient.builder();
		
		List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("5678", 3));
		
		
		return ratings.stream().map(rating ->{
			//Movie movie=restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
			
			
			Movie movie=builder.build()
			.get() //http method
			.uri("http://localhost:8082/movies/"+rating.getMovieId())
			.retrieve() //get response
			.bodyToMono(Movie.class) //move to single object, mono basically means promise that an object is going to be produced in future after asynchronous calls are done
			.block();
			
			//if the entire method is made reactive then an empty container gets returned to user and data is filled into it as it is received from the source
			
			return new CatalogItem(movie.getName(), "Test", rating.getI());
					}).
				collect(Collectors.toList());
		
	
	//return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));

	}
	*/
}
