package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mailconfiguration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int emailid;
	private String email;
	private String password;
	private String type;
	
	public Mailconfiguration() {
		
	}

	public int getEmailid() {
		return emailid;
	}

	public void setEmailid(int emailid) {
		this.emailid = emailid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
