package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReferralPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int planID;
	private String planName;
	private double planAmount;
	private double bonusPercentage;
	private String imageUrl;
	private long validty;
	private String discounts;
	
	public ReferralPlan() {
		
	}
	public int getPlanID() {
		return planID;
	}
	public void setPlanID(int planID) {
		this.planID = planID;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public double getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(double planAmount) {
		this.planAmount = planAmount;
	}
	public double getBonusPercentage() {
		return bonusPercentage;
	}
	public void setBonusPercentage(double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public long getValidty() {
		return validty;
	}
	public void setValidty(long validty) {
		this.validty = validty;
	}
	public String getDiscounts() {
		return discounts;
	}
	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}
	
	
}
