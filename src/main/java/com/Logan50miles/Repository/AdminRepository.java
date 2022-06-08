package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByEmailId(String emailId);

	List<Admin> findByRole(String role);
    
}
