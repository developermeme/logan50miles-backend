package com.Logan50miles.Model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class NazcaShoppingCart {
	
   
	private int orderNumber;
	
	private List<ItemInfo> cartItem;
	
	private double totalPrice;
	private double discount;
	private int addId;
	
	private double tax;
	
	public NazcaShoppingCart() {

		
	}
	
	public List<ItemInfo> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<ItemInfo> cartItem) {
		this.cartItem = cartItem;
	}


	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getAddId() {
		return addId;
	}

	public void setAddId(int addId) {
		this.addId = addId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}


}
