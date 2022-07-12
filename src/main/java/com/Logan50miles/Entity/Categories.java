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
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int catId;
	private String cName;
    private String curl;
	private String mobileurl;

	@ManyToOne
	@JoinColumn(name="maincat_id")
	private MainCategory maincatId;
	@OneToMany(mappedBy="catId")
	private List<Subcategory>  subcategories;
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}

	public MainCategory getMaincatId() {
		return maincatId;
	}
	public void setMaincatId(MainCategory maincatId) {
		this.maincatId = maincatId;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public List<Subcategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}
	public String getMobileurl() {
		return mobileurl;
	}
	public void setMobileurl(String mobileurl) {
		this.mobileurl = mobileurl;
	}
	
}
