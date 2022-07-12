package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.ShopAdmin;

public interface ShopAdminRepository extends JpaRepository<ShopAdmin, Integer>{
	 @Query("from ShopAdmin where shopName=:shopName")
	ShopAdmin findByShopName(String shopName);

	ShopAdmin findByShopId(int shopId);

}
