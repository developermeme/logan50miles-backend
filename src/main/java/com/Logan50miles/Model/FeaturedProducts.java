package com.Logan50miles.Model;

import java.util.List;

import com.Logan50miles.Entity.ProductImage;

public class FeaturedProducts {
	private int productCode;
	private String productName;
	private String productcode;
	private double price;
    private int offer;
	private String imageurl;
	private String sPhone;
	private String enddate;
    private String psize;
    private String pcolor;
	private String description;
	private String description1;
    private String pWeight;
    private String proSize;
    private String productHeight;
    private String productLength;
    private String deliveryTime;
    private String metal;
    private String stone;
    private String fabric;
    private String pattern;
	  
	private List<Ps> ps;
	private List<ProductImage> prodImg;
	
	public FeaturedProducts() {
		
	}

	public List<ProductImage> getProdImg() {
		return prodImg;
	}

	public void setProdImg(List<ProductImage> prodImg) {
		this.prodImg = prodImg;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOffer() {
		return offer;
	}

	public void setOffer(int offer) {
		this.offer = offer;
	}


	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
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

	public String getPsize() {
		return psize;
	}

	public void setPsize(String psize) {
		this.psize = psize;
	}


	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getMetal() {
		return metal;
	}

	public void setMetal(String metal) {
		this.metal = metal;
	}

	public String getStone() {
		return stone;
	}

	public void setStone(String stone) {
		this.stone = stone;
	}

	public String getpWeight() {
		return pWeight;
	}

	public void setpWeight(String pWeight) {
		this.pWeight = pWeight;
	}

	public String getProSize() {
		return proSize;
	}

	public void setProSize(String proSize) {
		this.proSize = proSize;
	}

	public String getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}

	public String getProductLength() {
		return productLength;
	}

	public void setProductLength(String productLength) {
		this.productLength = productLength;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
}
