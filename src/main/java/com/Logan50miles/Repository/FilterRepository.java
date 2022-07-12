package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Filters;

public interface FilterRepository extends JpaRepository<Filters, Integer>{
    @Query("from Filters where filtername=:filtername")
	List<Filters> findByFilterName(String filtername);

}
