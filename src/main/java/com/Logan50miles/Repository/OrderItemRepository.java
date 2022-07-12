package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, Integer>{

}
