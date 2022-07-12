package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.OrderStatus;

public interface OrderStatusrepository extends JpaRepository<OrderStatus, Integer>{
	@Query("from OrderStatus where id=:oid ")
	OrderStatus findByOrderId(int oid);

	OrderStatus findByOrdercode(String oid);

}
