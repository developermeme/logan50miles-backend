package com.Logan50miles.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserCartTemp {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int cartPId;
private String custId;
private int pId;
private int qty;
private String size;
private double price;

public UserCartTemp() {
	
}

public int getCartPId() {
	return cartPId;
}

public void setCartPId(int cartPId) {
	this.cartPId = cartPId;
}

public String getCustId() {
	return custId;
}

public void setCustId(String custId) {
	this.custId = custId;
}

public int getpId() {
	return pId;
}

public void setpId(int pId) {
	this.pId = pId;
}

public int getQty() {
	return qty;
}

public void setQty(int qty) {
	this.qty = qty;
}

public String getSize() {
	return size;
}

public void setSize(String size) {
	this.size = size;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

}
