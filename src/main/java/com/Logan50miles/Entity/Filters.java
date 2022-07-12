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
public class Filters {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int filterId;
	private String filtername;
	@OneToMany(mappedBy="filterId")
	private List<FilterType>  filterTypes;
	public Filters() {
		
	}
	public int getFilterId() {
		return filterId;
	}
	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}
	public String getFiltername() {
		return filtername;
	}
	public void setFiltername(String filtername) {
		this.filtername = filtername;
	}
	public List<FilterType> getFilterTypes() {
		return filterTypes;
	}
	public void setFilterTypes(List<FilterType> filterTypes) {
		this.filterTypes = filterTypes;
	}
	
}
