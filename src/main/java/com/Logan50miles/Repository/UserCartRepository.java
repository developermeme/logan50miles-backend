package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.UserCartTemp;

public interface UserCartRepository extends JpaRepository<UserCartTemp, Integer>{
    
	@Query("from UserCartTemp where custId=:cusId")
	List<UserCartTemp> findByCustomerId(String cusId);
    @Query("from UserCartTemp where custId=:cusId AND pId=:pId")
	UserCartTemp findByCusPid(int pId, String cusId);
    

}
