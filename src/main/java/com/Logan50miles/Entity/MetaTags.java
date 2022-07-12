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
public class MetaTags {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mid;
	private String description;
	private String keyword;
	private String twitter;
	private String facebook;
	private String instagram;
	private String linkedin;
	private String pintrest;
	@ManyToOne
	@JoinColumn(name="mc_Id")
	private Products mcId;
	public MetaTags() {
		
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getPintrest() {
		return pintrest;
	}

	public void setPintrest(String pintrest) {
		this.pintrest = pintrest;
	}

	public Products getMcId() {
		return mcId;
	}

	public void setMcId(Products mcId) {
		this.mcId = mcId;
	}
	
}
