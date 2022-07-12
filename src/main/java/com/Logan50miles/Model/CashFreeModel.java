package com.Logan50miles.Model;

public class CashFreeModel {
	private String order_id;
	private String order_currency;
	private long order_amount;
	private CustomerData customer_details;
	public CashFreeModel() {
		
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_currency() {
		return order_currency;
	}
	public void setOrder_currency(String order_currency) {
		this.order_currency = order_currency;
	}
	public long getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(long order_amount) {
		this.order_amount = order_amount;
	}
	public CustomerData getCustomer_details() {
		return customer_details;
	}
	public void setCustomer_details(CustomerData customer_details) {
		this.customer_details = customer_details;
	}
	
	
}
