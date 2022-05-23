package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Events;

public interface EventsRepository extends JpaRepository<Events, Integer>{

}
