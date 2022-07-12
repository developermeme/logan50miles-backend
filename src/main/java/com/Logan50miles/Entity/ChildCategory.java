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
public class ChildCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int childcatId;
	private String cName;
    private String curl;
    
    @ManyToOne
	@JoinColumn(name="subcat_id")
	private Subcategory subcatId;

	public int getChildcatId() {
		return childcatId;
	}

	public void setChildcatId(int childcatId) {
		this.childcatId = childcatId;
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

	public Subcategory getSubcatId() {
		return subcatId;
	}

	public void setSubcatId(Subcategory subcatId) {
		this.subcatId = subcatId;
	}
    
    
}
