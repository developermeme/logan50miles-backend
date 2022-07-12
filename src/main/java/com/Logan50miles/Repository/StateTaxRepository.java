package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.StateTax;



public interface StateTaxRepository extends JpaRepository<StateTax, Integer>{
    @Query("from StateTax where tid=:id")
	StateTax findByTaxId(int id);

}
