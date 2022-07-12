package com.Logan50miles.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{
	@Query("Select max(o.orderId) as value  from Orders o")
	String findOrderId();
    @Query("from Orders where userId=:cusId")
	List<Orders> findByCusId(String cusId);
    @Query("from Orders where shopId=:shopId")
	List<Orders> findByShopId(String shopId);
    @Query("from Orders where date>=:fd and date<=:td and shopId=:id  ORDER BY orderId DESC")
	List<Orders> findByDates(Date fd, Date td, String id);
    @Query("from Orders where date>=:fd and date<=:td  ORDER BY orderId DESC")
	List<Orders> findByDates(Date fd, Date td);
    @Query(" from Orders m where m.orderId=:oId")
	Orders findByOrderNumber(int oId);
	Orders findByOrdercode(String oId);

}
