package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	List<ProductImage> findByImageUrl(String string);

	List<ProductImage> findByMcId(int i);
  
}
