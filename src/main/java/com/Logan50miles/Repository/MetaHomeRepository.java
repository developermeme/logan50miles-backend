package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.MetaTagHome;

public interface MetaHomeRepository extends JpaRepository<MetaTagHome, Integer>{
    @Query("from MetaTagHome where hid=:hid")
	MetaTagHome findAllByHomeId(int hid);

}
