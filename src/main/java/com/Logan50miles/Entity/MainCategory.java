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
public class MainCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int maincatId;
	private String mainCatName;
	private String imageUrl;
	private String mobileurl;
	
	@OneToMany(mappedBy="maincatId")
	private List<Categories>  categories;
	@OneToMany(mappedBy="maincatId")
	private List<Filteration>  filteration;
	
	public MainCategory() {
		
	}

	public int getMaincatId() {
		return maincatId;
	}

	public void setMaincatId(int maincatId) {
		this.maincatId = maincatId;
	}

	public String getMainCatName() {
		return mainCatName;
	}

	public void setMainCatName(String mainCatName) {
		this.mainCatName = mainCatName;
	}

	public List<Categories> getCategories() {
		return categories;
	}

	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Filteration> getFilteration() {
		return filteration;
	}

	public void setFilteration(List<Filteration> filteration) {
		this.filteration = filteration;
	}

	public String getMobileurl() {
		return mobileurl;
	}

	public void setMobileurl(String mobileurl) {
		this.mobileurl = mobileurl;
	}
	
}
