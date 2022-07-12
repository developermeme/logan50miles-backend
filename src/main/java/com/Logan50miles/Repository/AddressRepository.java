package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {

	 @Query("from Address where  id=:tableNo")
	 Address findByAddId(int tableNo);

}
