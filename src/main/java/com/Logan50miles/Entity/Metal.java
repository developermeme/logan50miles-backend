package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Metal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String metals;
	
	public Metal() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetals() {
		return metals;
	}

	public void setMetals(String metals) {
		this.metals = metals;
	}
	
}
