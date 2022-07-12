package com.Logan50miles.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Logan50miles.Entity.AdminScreen;
import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Model.PreOrderItemInfo;
import com.Logan50miles.Service.DashboarService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("ophelia")
public class DashboardController {
	
	@Autowired
	private DashboarService dashboarService;

	@GetMapping("orderhistory")
	public List<Orders> getOrdersByCusId(String cusId) {
		return dashboarService.getOrdersByCusId(cusId);
	}

	@GetMapping("preorderhistory")
	public List<PreOrderItemInfo> getPreOrderHistoryBycusId(String cusId) {
		return dashboarService.getPreOrdersBycusId(cusId);
	}

	@GetMapping("preorderhistoryshop")
	public List<PreOrderItemInfo> getPreOrderByshop(String shopId) {
		return dashboarService.getPreOrdersByshopId(shopId);
	}

	@GetMapping("allorders")
	public List<Orders> getAllOrders() {
		return dashboarService.getOrders();
	}

	@GetMapping("admin/home")
	public AdminScreen getAdminScreen() {
		return dashboarService.getData();
	}

	@GetMapping("admin/search")
	public Orders searchOrder(int oId) {
		return dashboarService.searchOrder(oId);
	}

	@GetMapping("admin/search/productcode")
	public Orders searchOrder(String oId) {
		return dashboarService.searchOrder(oId);
	}
}
