package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Tickets;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {

}
