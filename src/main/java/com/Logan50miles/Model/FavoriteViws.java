package com.Logan50miles.Model;

import java.util.List;

import com.Logan50miles.Entity.ProductImage;

public class FavoriteViws {
	private int favId;
	private int mcId;
	private String pName;
	private int offer;
	private double price;
	private String sPhone;
	private String endDate;
    private String quantity;
    private String descpription;
    private String pcolor;
    private String deliveryTime;
    private String imageurl;
    List<Ps> ps;
    List<ProductImage> pIm;

	public List<ProductImage> getpIm() {
		return pIm;
	}

	public void setpIm(List<ProductImage> pIm) {
		this.pIm = pIm;
	}

	public int getMcId() {
		return mcId;
	}
	public void setMcId(int mcId) {
		this.mcId = mcId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getOffer() {
		return offer;
	}
	public void setOffer(int offer) {
		this.offer = offer;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getsPhone() {
		return sPhone;
	}
	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDescpription() {
		return descpription;
	}
	public void setDescpription(String descpription) {
		this.descpription = descpription;
	}

	public int getFavId() {
		return favId;
	}
	public void setFavId(int favId) {
		this.favId = favId;
	}
	public String getPcolor() {
		return pcolor;
	}
	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public List<Ps> getPs() {
		return ps;
	}
	public void setPs(List<Ps> ps) {
		this.ps = ps;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
}
