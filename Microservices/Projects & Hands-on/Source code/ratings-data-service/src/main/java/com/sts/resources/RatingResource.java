package com.sts.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.models.Rating;
import com.sts.models.UserRating;

@RestController()
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}
	
	
	@GetMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable String userId) {
		List<Rating> ratings = Arrays.asList(new Rating("22", 4), new Rating("500", 3));
		UserRating userRating=new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
