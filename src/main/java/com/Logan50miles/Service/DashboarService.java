package com.Logan50miles.Service;

import java.util.List;

import com.Logan50miles.Entity.AdminScreen;
import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Model.PreOrderItemInfo;

public interface DashboarService {

	List<Orders> getOrdersByCusId(String cusId);

	List<PreOrderItemInfo> getPreOrdersBycusId(String cusId);

	List<PreOrderItemInfo> getPreOrdersByshopId(String shopId);

	List<Orders> getOrders();

	AdminScreen getData();

	Orders searchOrder(int oId);

	Orders searchOrder(String oId);

}
