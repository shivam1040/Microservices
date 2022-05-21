package com.sts.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sts.models.CatalogItem;
import com.sts.models.Movie;
import com.sts.models.Rating;
import com.sts.models.UserRating;

@Service
public class Services {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallUserRating")
	public UserRating getUserRating(@PathVariable String userId) {
		return restTemplate.getForObject("http://Ratings/ratingsdata/users/"+userId, UserRating.class);
	}
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie=restTemplate.getForObject("http://Movie/movies/"+rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), "Test", rating.getI());
	}
	
	private CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("NA", "", rating.getI());
	}
	
	private UserRating getFallUserRating(@PathVariable String userId) {
		UserRating userRating=new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
