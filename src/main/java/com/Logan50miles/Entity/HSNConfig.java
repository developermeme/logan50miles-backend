package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HSNConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer hsnid;
	private String hsnnumber;
	private double hsnpercentage;
	public HSNConfig() {
		
	}
	public Integer getHsnid() {
		return hsnid;
	}
	public void setHsnid(Integer hsnid) {
		this.hsnid = hsnid;
	}
	public String getHsnnumber() {
		return hsnnumber;
	}
	public void setHsnnumber(String hsnnumber) {
		this.hsnnumber = hsnnumber;
	}
	public double getHsnpercentage() {
		return hsnpercentage;
	}
	public void setHsnpercentage(double hsnpercentage) {
		this.hsnpercentage = hsnpercentage;
	}
	
}
