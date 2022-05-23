package com.Logan50miles.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.BookingEvents;
import com.Logan50miles.Entity.Events;
import com.Logan50miles.Util.ResourceNotFoundException;

public interface EventService {

	List<Events> getEvents();

	Events addEvents(Events events, MultipartFile file) throws IOException;

	Events getEvent(int id) throws ResourceNotFoundException;

	Events updateStatus(int id) throws ResourceNotFoundException;

	String deleteEvents(int id);

	BookingEvents addBookingEvents(BookingEvents bookingEvents, MultipartFile file, MultipartFile file1)throws IOException;

	List<BookingEvents> getBookings();

	List<BookingEvents> getByPhone(String phone);

	String deleteBookingEvents(int id);
	

}
