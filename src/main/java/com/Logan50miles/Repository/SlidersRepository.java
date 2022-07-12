package com.Logan50miles.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Sliders;

public interface SlidersRepository extends JpaRepository<Sliders, Integer>{
    @Query("from Sliders where banId=:id")
	Sliders findByBannerId(int id);


}
