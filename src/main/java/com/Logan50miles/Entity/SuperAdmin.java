package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class SuperAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int supAdminId;
	private String supUserName;
	private String supPaswword;
	
	public SuperAdmin() {
		
	}

	public int getSupAdminId() {
		return supAdminId;
	}

	public void setSupAdminId(int supAdminId) {
		this.supAdminId = supAdminId;
	}

	public String getSupUserName() {
		return supUserName;
	}

	public void setSupUserName(String supUserName) {
		this.supUserName = supUserName;
	}

	public String getSupPaswword() {
		return supPaswword;
	}

	public void setSupPaswword(String supPaswword) {
		this.supPaswword = supPaswword;
	}
	
}
