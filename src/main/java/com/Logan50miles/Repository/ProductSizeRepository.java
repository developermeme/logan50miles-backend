package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    @Query("from ProductSize where sid=:id")
	ProductSize findBySid(int id);
    @Query("from ProductSize where sku=:code")
	ProductSize findBySku(String code);

}
