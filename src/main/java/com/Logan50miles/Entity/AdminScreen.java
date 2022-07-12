package com.Logan50miles.Entity;

import java.util.List;

public class AdminScreen {
private int orders;
private List<Orders> ordersList;
private int completed;
private List<Orders> ordersCompleted;
private int canceled;
private List<Orders> ordersCanceled;
private int progress;
private List<Orders> ordersProgress;

public AdminScreen() {
	
}

public int getOrders() {
	return orders;
}

public void setOrders(int orders) {
	this.orders = orders;
}

public int getCompleted() {
	return completed;
}

public void setCompleted(int completed) {
	this.completed = completed;
}

public int getCanceled() {
	return canceled;
}

public void setCanceled(int canceled) {
	this.canceled = canceled;
}

public int getProgress() {
	return progress;
}

public void setProgress(int progress) {
	this.progress = progress;
}

public List<Orders> getOrdersList() {
	return ordersList;
}

public void setOrdersList(List<Orders> ordersList) {
	this.ordersList = ordersList;
}

public List<Orders> getOrdersCompleted() {
	return ordersCompleted;
}

public void setOrdersCompleted(List<Orders> ordersCompleted) {
	this.ordersCompleted = ordersCompleted;
}

public List<Orders> getOrdersCanceled() {
	return ordersCanceled;
}

public void setOrdersCanceled(List<Orders> ordersCanceled) {
	this.ordersCanceled = ordersCanceled;
}

public List<Orders> getOrdersProgress() {
	return ordersProgress;
}

public void setOrdersProgress(List<Orders> ordersProgress) {
	this.ordersProgress = ordersProgress;
}

}
