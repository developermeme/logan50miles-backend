package com.Logan50miles.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Logan50miles.Entity.AdminScreen;
import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Model.PreOrderItemInfo;
import com.Logan50miles.Repository.OrderRepository;
import com.Logan50miles.Service.DashboarService;


@Service
public class DashboardImpl implements DashboarService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Orders> getOrdersByCusId(String cusId) {
		return orderRepository.findByCusId(cusId);
	}

	@Override
	public List<PreOrderItemInfo> getPreOrdersBycusId(String cusId) {

		List<Orders> po = orderRepository.findByCusId(cusId);

		List<PreOrderItemInfo> preOrd = new ArrayList<PreOrderItemInfo>();
		for (Orders ord : po) {

			for (int i = 0; i < ord.getItems().size(); i++) {

				if (ord.getItems().get(i).getItemType().equalsIgnoreCase("PreOrder")) {
					PreOrderItemInfo info = new PreOrderItemInfo();

					info.setName(ord.getItems().get(i).getName());
					info.setQuantity(ord.getItems().get(i).getQuantity());
					info.setOrderId(ord.getItems().get(i).getOrderId().getOrderId());
					info.setItemType(ord.getItems().get(i).getItemType());
					info.setSubt(ord.getItems().get(i).getSubt());
					info.setOrderItemId(ord.getItems().get(i).getOrderItemId());
					preOrd.add(info);

				}

			}
		}
		return preOrd;

	}

	@Override
	public List<PreOrderItemInfo> getPreOrdersByshopId(String shopId) {

		List<Orders> po = orderRepository.findByShopId(shopId);

		List<PreOrderItemInfo> preOrd = new ArrayList<PreOrderItemInfo>();
		for (Orders ord : po) {

			for (int i = 0; i < ord.getItems().size(); i++) {

				if (ord.getItems().get(i).getItemType().equalsIgnoreCase("PreOrder")) {
					PreOrderItemInfo info = new PreOrderItemInfo();

					info.setName(ord.getItems().get(i).getName());
					info.setQuantity(ord.getItems().get(i).getQuantity());
					info.setOrderId(ord.getItems().get(i).getOrderId().getOrderId());
					info.setItemType(ord.getItems().get(i).getItemType());
					info.setSubt(ord.getItems().get(i).getSubt());
					info.setOrderItemId(ord.getItems().get(i).getOrderItemId());
					info.setShopId(ord.getShopId());

					preOrd.add(info);

				}

			}
		}

		return preOrd;
	}

	@Override
	public List<Orders> getOrders() {
		return orderRepository.findAll();
	}

	@Override
	public AdminScreen getData() {
		AdminScreen adminScreen = new AdminScreen();
		int completed = 0;
		int canceled = 0;
		int progress = 0;

		List<Orders> op = new ArrayList<Orders>();
		List<Orders> oc = new ArrayList<Orders>();
		List<Orders> ocan = new ArrayList<Orders>();
		List<Orders> orders = orderRepository.findAll();

		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOrderStatus().getStatus() == "completed") {
				completed += 1;
				oc.add(orders.get(i));
				adminScreen.setCompleted(completed);
			} else if (orders.get(i).getOrderStatus().getStatus() == "canceled") {
				canceled += 1;
				ocan.add(orders.get(i));
				adminScreen.setCanceled(canceled);
			} else {
				progress += 1;
				op.add(orders.get(i));
				adminScreen.setProgress(progress);
			}

		}
		adminScreen.setOrdersCanceled(ocan);
		adminScreen.setOrdersCompleted(oc);
		adminScreen.setOrdersList(orders);
		adminScreen.setOrdersProgress(op);
		adminScreen.setOrders(orders.size());
		return adminScreen;
	}

	@Override
	public Orders searchOrder(int oId) {
		return orderRepository.findByOrderNumber(oId);
	}

	@Override
	public Orders searchOrder(String oId) {
		return orderRepository.findByOrdercode(oId);
	}

}
