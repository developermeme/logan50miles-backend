package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.HSNConfig;

public interface HSNRepository extends JpaRepository<HSNConfig, Integer> {

	HSNConfig findByHsnnumber(String hsn);

}
