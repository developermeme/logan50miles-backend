package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.SliderMobile;

public interface SliderMobileRepository extends JpaRepository<SliderMobile, Integer>{
    @Query("from SliderMobile where banId=:id")
	SliderMobile findBybanId(int id);


}
