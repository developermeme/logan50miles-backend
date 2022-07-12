package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShopAdmin {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int shopId;
private String shopName;
private String password;
private String country;
private String state;
private String city;
private Boolean status;
private String vendorUrl;
private String vendorName;
public ShopAdmin() {
	
}

public int getShopId() {
	return shopId;
}

public void setShopId(int shopId) {
	this.shopId = shopId;
}

public String getShopName() {
	return shopName;
}

public void setShopName(String shopName) {
	this.shopName = shopName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public Boolean getStatus() {
	return status;
}

public void setStatus(Boolean status) {
	this.status = status;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getVendorUrl() {
	return vendorUrl;
}

public void setVendorUrl(String vendorUrl) {
	this.vendorUrl = vendorUrl;
}

public String getVendorName() {
	return vendorName;
}

public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
}

}
