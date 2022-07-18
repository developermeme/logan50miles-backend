package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.BookingTickets;

public interface BookingTicketsRepository extends JpaRepository<BookingTickets, Integer>{

	BookingTickets findByUseremail(String email);

}
