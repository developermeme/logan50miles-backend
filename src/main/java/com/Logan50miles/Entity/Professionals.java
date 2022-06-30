package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Professionals {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;
	private String fname;
	private String lname;
	private String pimg;
	private String phoneno;
	private String email;
	private String description;
	private String userReferral;
	private String affiliateCode;
	private String password;
	private boolean isEnabled;
	private String dob;
	private String state;
	private String city;
	private String country;
	private String instaurl;
	private String fburl;
	private String imgproof;
	private String videoproof;
	private String status;
	private String eventlevel;
	
	public Professionals() {
		
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserReferral() {
		return userReferral;
	}

	public void setUserReferral(String userReferral) {
		this.userReferral = userReferral;
	}

	public String getAffiliateCode() {
		return affiliateCode;
	}

	public void setAffiliateCode(String affiliateCode) {
		this.affiliateCode = affiliateCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getInstaurl() {
		return instaurl;
	}

	public void setInstaurl(String instaurl) {
		this.instaurl = instaurl;
	}

	public String getFburl() {
		return fburl;
	}

	public void setFburl(String fburl) {
		this.fburl = fburl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getImgproof() {
		return imgproof;
	}

	public void setImgproof(String imgproof) {
		this.imgproof = imgproof;
	}

	public String getVideoproof() {
		return videoproof;
	}

	public void setVideoproof(String videoproof) {
		this.videoproof = videoproof;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEventlevel() {
		return eventlevel;
	}

	public void setEventlevel(String eventlevel) {
		this.eventlevel = eventlevel;
	}

	
	
}
