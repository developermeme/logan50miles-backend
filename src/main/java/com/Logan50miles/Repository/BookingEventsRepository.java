package com.Logan50miles.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.BookingEvents;

public interface BookingEventsRepository extends JpaRepository<BookingEvents, Integer>{

	List<BookingEvents> findByMobile(String phone);

	 
}
