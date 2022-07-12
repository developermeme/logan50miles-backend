package com.Logan50miles.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int orderItemId;
	@Column(name = "name")
	private String name;
	@Column(name = "quantity")
	private int quantity;
	private double subt;;
	private String itemType;
	private String size;
	private String color;
	private String imgUrl;
	private double taxAmount;
	private double deliveryCharge;
	private String sku;
	private String status;
	private String gsthsn;
	private String gstern;
	private String discount;
	private String gsttaxratecgstn;
	private String gsttaxratesgstn;
	private String gsttaxrateigstn;
	private String gsttaxtotal;
	private String gsttaxcgstn;
	private String gsttaxsgstn;
	private String gsttaxigstn;
	private double length;
	private double width;
	private double height;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders orderId;

	public OrderItems() {
		
	}

	public OrderItems(Orders orders) {
		this.orderId = orders;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Orders getOrderId() {
		return orderId;
	}

	public void setOrderId(Orders orderId) {
		this.orderId = orderId;
	}

	public double getSubt() {
		return subt;
	}

	public void setSubt(double subt) {
		this.subt = subt;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGsthsn() {
		return gsthsn;
	}

	public void setGsthsn(String gsthsn) {
		this.gsthsn = gsthsn;
	}

	public String getGstern() {
		return gstern;
	}

	public void setGstern(String gstern) {
		this.gstern = gstern;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getGsttaxratecgstn() {
		return gsttaxratecgstn;
	}

	public void setGsttaxratecgstn(String gsttaxratecgstn) {
		this.gsttaxratecgstn = gsttaxratecgstn;
	}

	public String getGsttaxratesgstn() {
		return gsttaxratesgstn;
	}

	public void setGsttaxratesgstn(String gsttaxratesgstn) {
		this.gsttaxratesgstn = gsttaxratesgstn;
	}

	public String getGsttaxrateigstn() {
		return gsttaxrateigstn;
	}

	public void setGsttaxrateigstn(String gsttaxrateigstn) {
		this.gsttaxrateigstn = gsttaxrateigstn;
	}

	public String getGsttaxtotal() {
		return gsttaxtotal;
	}

	public void setGsttaxtotal(String gsttaxtotal) {
		this.gsttaxtotal = gsttaxtotal;
	}

	public String getGsttaxcgstn() {
		return gsttaxcgstn;
	}

	public void setGsttaxcgstn(String gsttaxcgstn) {
		this.gsttaxcgstn = gsttaxcgstn;
	}

	public String getGsttaxsgstn() {
		return gsttaxsgstn;
	}

	public void setGsttaxsgstn(String gsttaxsgstn) {
		this.gsttaxsgstn = gsttaxsgstn;
	}

	public String getGsttaxigstn() {
		return gsttaxigstn;
	}

	public void setGsttaxigstn(String gsttaxigstn) {
		this.gsttaxigstn = gsttaxigstn;
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
