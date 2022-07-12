package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class Ratings {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ratingid;
	private double rating;
	private int numbers;
	
	@ManyToOne
	@JoinColumn(name="mc_Id")
	private Products mcId;
	public Ratings() {
		
	}
	public Integer getRatingid() {
		return ratingid;
	}
	public void setRatingid(Integer ratingid) {
		this.ratingid = ratingid;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	public Products getMcId() {
		return mcId;
	}
	public void setMcId(Products mcId) {
		this.mcId = mcId;
	}
	
}
