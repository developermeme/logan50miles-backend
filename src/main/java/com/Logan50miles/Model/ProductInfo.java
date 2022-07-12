package com.Logan50miles.Model;

import com.Logan50miles.Entity.Products;

public class ProductInfo {

	private int productCode;
	private String sku;
	private String productName;
	private double price;
    private int offer;
	private String imageurl;
	private String sPhone;
	private String enddate;
	private String size;
	private String color;
	private String descpription;
	private int cartId;
	private int qty;
	private int minqty;
	private double tax;
    private String gsthsn;
	private double length;
	private double width;
	private double height;
	public ProductInfo(Products product) {

		this.productCode = product.getMcId();
		this.productName = product.getProductname();
		this.color=product.getProductcolor();
		this.imageurl=product.getImageurl();
		this.offer=product.getOffer();
		this.sPhone=product.getPhone();
		this.enddate=product.getDate();
		this.descpription=product.getDescpription();
		this.minqty=product.getMinqty();
		this.tax=product.getTax();
		this.sku=product.getProductcode();
		this.gsthsn=product.getGsthsn();

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


	public String getImageurl() {
		return imageurl;
	}


	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
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


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getDescpription() {
		return descpription;
	}


	public void setDescpription(String descpription) {
		this.descpription = descpription;
	}


	public int getCartId() {
		return cartId;
	}


	public void setCartId(int cartId) {
		this.cartId = cartId;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public int getMinqty() {
		return minqty;
	}


	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}


	public double getTax() {
		return tax;
	}


	public void setTax(double tax) {
		this.tax = tax;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public String getGsthsn() {
		return gsthsn;
	}


	public void setGsthsn(String gsthsn) {
		this.gsthsn = gsthsn;
	}


	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


}
