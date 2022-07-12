package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.MetaTags;

public interface MetaTagRepository extends JpaRepository<MetaTags, Integer>{
    @Query("from MetaTags where mid=:mid")
	MetaTags findAllByMId(int mid);

}
