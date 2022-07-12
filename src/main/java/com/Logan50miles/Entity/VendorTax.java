package com.Logan50miles.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class VendorTax {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int vid;
	private String country;
	@OneToMany(mappedBy="vid")
	private List<StateTax>  stateTax;
	public VendorTax() {
		
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<StateTax> getStateTax() {
		return stateTax;
	}
	public void setStateTax(List<StateTax> stateTax) {
		this.stateTax = stateTax;
	}

}
