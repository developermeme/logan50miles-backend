package com.Logan50miles.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	private String uPhone;
	private String fname;
	private String lname;
	private String iUmg;
	private String email;
	private String userReferral;
	private String affiliateCode;
	private String password;
	private boolean isEnabled;
	
	@OneToMany(mappedBy = "userid")
	private List<Address> address;

	public User() {
		
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
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

	public String getiUmg() {
		return iUmg;
	}

	public void setiUmg(String iUmg) {
		this.iUmg = iUmg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
}
