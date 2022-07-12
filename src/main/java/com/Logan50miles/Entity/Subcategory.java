package com.Logan50miles.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subcatId;
	private String cName;
    private String curl;
    
	@OneToMany(mappedBy="subcatId")
	private List<ChildCategory>  childcategories;
    @ManyToOne
	@JoinColumn(name="cat_id")
	private Categories catId;

	public int getSubcatId() {
		return subcatId;
	}

	public void setSubcatId(int subcatId) {
		this.subcatId = subcatId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	public Categories getCatId() {
		return catId;
	}

	public void setCatId(Categories catId) {
		this.catId = catId;
	}

	public List<ChildCategory> getChildcategories() {
		return childcategories;
	}

	public void setChildcategories(List<ChildCategory> childcategories) {
		this.childcategories = childcategories;
	}
    
    
}
