package com.Logan50miles.Entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
@Table(name = "orders")
public class Orders {

	@Id
	@Column(name = "order_id")
	private Integer orderId;
	private String ordercode;
	private String paymentStatus;
	private String userId;
	private int addId;
	private String shopId;
	private String tax;
	private String tnxid;
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "orderId")
	private List<OrderItems> items;

	@Column(name = "price")
	private double price;
	
	public Orders(Integer orderId, String userId, List<OrderItems> items, double price, Date date,int addId,String shopId) {
		
		this.orderId = orderId;
		this.userId = userId;
		this.items = items;
		this.price = price;
		this.date = date;
		this.addId=addId;
		this.shopId=shopId;
	//	this.orderType=orderType;
			
	}

	@Column(name="order_date")
	private Date date;
	private String name;
	private String phone;
	private String flatNo;
	private String Street;
	private String landMark;
	private String city;
	private String pin;
	private String state;
	private String country;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orders",cascade=CascadeType.ALL) 
	private OrderStatus orderStatus;
	
public Orders() {
		
	}
	

	public int getAddId() {
	return addId;
}
public void setAddId(int addId) {
	this.addId = addId;
}


	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderItems> getItems() {
		return items;
	}

	public void setItems(List<OrderItems> items) {
		this.items = items;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}


	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}



	public String getTax() {
		return tax;
	}


	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getOrdercode() {
		return ordercode;
	}


	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}


	public String getTnxid() {
		return tnxid;
	}


	public void setTnxid(String tnxid) {
		this.tnxid = tnxid;
	}

}
