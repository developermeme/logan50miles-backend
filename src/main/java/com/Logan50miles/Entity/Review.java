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
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private Integer rid;
private String name;
private String review;
private int like;
private int dislike;
private int start;
private int rating;
@ManyToOne
@JoinColumn(name="mc_Id")
private Products mcId;
private Review() {
	
}

public Integer getRid() {
	return rid;
}

public void setRid(Integer rid) {
	this.rid = rid;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getReview() {
	return review;
}

public void setReview(String review) {
	this.review = review;
}

public int getLike() {
	return like;
}

public void setLike(int like) {
	this.like = like;
}

public int getDislike() {
	return dislike;
}

public void setDislike(int dislike) {
	this.dislike = dislike;
}

public int getStart() {
	return start;
}

public void setStart(int start) {
	this.start = start;
}

public Products getMcId() {
	return mcId;
}

public void setMcId(Products mcId) {
	this.mcId = mcId;
}

public int getRating() {
	return rating;
}

public void setRating(int rating) {
	this.rating = rating;
}

}
