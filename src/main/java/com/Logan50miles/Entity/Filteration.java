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
public class Filteration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int filtypeId;
	private String filtertype;
	@ManyToOne
	@JoinColumn(name="maincat_id")
	private MainCategory maincatId;
	public Filteration() {
		
	}
	public int getFiltypeId() {
		return filtypeId;
	}
	public void setFiltypeId(int filtypeId) {
		this.filtypeId = filtypeId;
	}
	public String getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype;
	}
	public MainCategory getMaincatId() {
		return maincatId;
	}
	public void setMaincatId(MainCategory maincatId) {
		this.maincatId = maincatId;
	}
	
}
