package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sliders {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int banId;
private String bannerUrl;
private String keyword;
private String date;
private String description;
public Sliders() {
	
}
public int getBanId() {
	return banId;
}
public void setBanId(int banId) {
	this.banId = banId;
}

public String getBannerUrl() {
	return bannerUrl;
}
public void setBannerUrl(String bannerUrl) {
	this.bannerUrl = bannerUrl;
}
public String getKeyword() {
	return keyword;
}
public void setKeyword(String keyword) {
	this.keyword = keyword;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
