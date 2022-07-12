package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer>{

	SuperAdmin findBySupUserName(String userName);

}
