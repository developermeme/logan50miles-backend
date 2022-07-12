package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class OrderStatus {
@Id
private int id;
private String status;
private String sPhone;
private String ordercode;
@OneToOne(fetch = FetchType.LAZY) 
@JoinColumn(name = "id") 
private Orders orders;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getsPhone() {
	return sPhone;
}

public void setsPhone(String sPhone) {
	this.sPhone = sPhone;
}

public Orders getOrders() {
	return orders;
}

public void setOrders(Orders orders) {
	this.orders = orders;
}

public String getOrdercode() {
	return ordercode;
}

public void setOrdercode(String ordercode) {
	this.ordercode = ordercode;
}



}
