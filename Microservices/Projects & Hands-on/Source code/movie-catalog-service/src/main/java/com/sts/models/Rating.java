package com.sts.models;

public class Rating {
	String movieId;
	int rating;
	public Rating(String movieId, int i) {
		super();
		this.movieId = movieId;
		this.rating = i;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public int getI() {
		return rating;
	}
	public void setI(int i) {
		this.rating = i;
	}
	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
