package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Favourites;


public interface FavouriteRepository extends JpaRepository<Favourites, Integer>{
    
	List<Favourites> getByUserid(String id);
    
    @Query("from Favourites f where f.productId=:productId and f.userid=:userid")
	Favourites getFav(Integer productId, String userid);
    @Query("from Favourites f where f.id=:id")
	void deleteByFavId(Integer id);
   
}
