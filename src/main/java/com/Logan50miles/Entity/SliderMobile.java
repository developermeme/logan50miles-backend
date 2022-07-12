package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SliderMobile {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int banId;
private String bannerUrl;
public SliderMobile() {
	
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

}