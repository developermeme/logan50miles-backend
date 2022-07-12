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
public class ProductSize {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int sid;
private String psize;
private double price;
private String sku;
private int qty;
private double length;
private double width;
private double height;
@ManyToOne
@JoinColumn(name="mc_Id")
private Products mcId;
public ProductSize(Products p) {
	this.mcId=p;
}
public ProductSize() {
	
}

public int getSid() {
	return sid;
}

public void setSid(int sid) {
	this.sid = sid;
}

public String getPsize() {
	return psize;
}

public void setPsize(String psize) {
	this.psize = psize;
}

public int getQty() {
	return qty;
}

public void setQty(int qty) {
	this.qty = qty;
}

public Products getMcId() {
	return mcId;
}

public void setMcId(Products mcId) {
	this.mcId = mcId;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
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
