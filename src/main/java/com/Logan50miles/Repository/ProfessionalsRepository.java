package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Professionals;

public interface ProfessionalsRepository extends JpaRepository<Professionals, Integer>{

	Professionals findByEmail(String email);

	List<Professionals> findAllByEmail(String mail);
	
}
