package com.Logan50miles.Entity;

import java.util.Date;
import java.util.List;

import com.Logan50miles.Model.Ps;

public class PreOrderProducts {
	private int mcId;
	private String pName;
	private String productcode;
	private int offer;
	private double price;
	private String sPhone;
	private String pcolor;
	private String deliveryTime;
	private int minqty;
	private Date endDate;
	private String imageurl;
	
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	private List<Ps> ps;
	List<ProductImage> productImages;

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public PreOrderProducts() {
		
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public int getMinqty() {
		return minqty;
	}

	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}

	public List<Ps> getPs() {
		return ps;
	}

	public void setPs(List<Ps> ps) {
		this.ps = ps;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

}
