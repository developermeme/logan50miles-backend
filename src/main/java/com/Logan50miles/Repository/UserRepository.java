package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);

}
