package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.ProductSpecification;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Integer>{
    @Query("from ProductSpecification where spid=:spid")
	ProductSpecification findBySpId(int spid);

	List<ProductSpecification> findByMcId(int mcId);

	List<ProductSpecification> findBySpecification(String string);

}
