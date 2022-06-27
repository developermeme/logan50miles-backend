package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.PlayerProfile;

public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Integer> {

	List<PlayerProfile> findAllByPid(int pid);

}
