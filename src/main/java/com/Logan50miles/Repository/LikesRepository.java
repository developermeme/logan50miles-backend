package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{

	
	Likes findByPpidAndUserId(int ppid, String userid);

	List<Likes> findByPpid(int ppid);

	
}
