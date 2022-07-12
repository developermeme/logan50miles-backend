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
public class ProductSpecification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int spid;
	private String specification;
	@ManyToOne
	@JoinColumn(name="mc_Id")
	private Products mcId;
public  ProductSpecification() {
	
}
public int getSpid() {
	return spid;
}
public void setSpid(int spid) {
	this.spid = spid;
}
public String getSpecification() {
	return specification;
}
public void setSpecification(String specification) {
	this.specification = specification;
}
public Products getMcId() {
	return mcId;
}
public void setMcId(Products mcId) {
	this.mcId = mcId;
}

}
