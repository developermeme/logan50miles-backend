package com.Logan50miles.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.VendorTax;

public interface VendorTaxRepository extends JpaRepository<VendorTax, Integer>{
    @Query("from VendorTax where country=:country")
	VendorTax findByCusId(String country);

}
