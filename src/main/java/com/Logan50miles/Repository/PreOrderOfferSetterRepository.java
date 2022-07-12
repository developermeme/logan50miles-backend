package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.PreOrderOfferSetter;

public interface PreOrderOfferSetterRepository extends  JpaRepository<PreOrderOfferSetter, Integer>{
    @Query("from PreOrderOfferSetter where id=:id")
	PreOrderOfferSetter findByPosId(int id);

}
