package com.Logan50miles.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.BookingEvents;
import com.Logan50miles.Entity.Events;
import com.Logan50miles.Service.EventService;
import com.Logan50miles.Util.ResourceNotFoundException;

@RestController
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("get/events") 
	public List<Events> getEvents(){ 
		return eventService.getEvents() ; 
	} 

	@PostMapping("add/events") 
	public Events addEvents(Events events, MultipartFile file) throws IOException{ 
		return eventService.addEvents(events, file); 
	} 

	@GetMapping("get/event") 
	public Events getEvent(int id) throws ResourceNotFoundException { 
		return eventService.getEvent(id); 
	} 

	@PutMapping("update/event/status") 
	public Events updateStatus(int id) throws ResourceNotFoundException{ 
		return eventService.updateStatus(id); 
	} 

	@DeleteMapping("delete/events") 
	public String deleteEvents(int id){ 
		return eventService.deleteEvents(id); 
	} 

	@PostMapping("add/booking") 
	public BookingEvents addBookingEvents(BookingEvents bookingEvents, MultipartFile file, MultipartFile file1) throws IOException{ 
		return eventService.addBookingEvents(bookingEvents, file, file1); 
	} 
 
	@GetMapping("get/booking") 
	public List<BookingEvents> getBookings(){ 
		return eventService.getBookings(); 
	} 

	@GetMapping("get/bookingevents/phone") 
	public List<BookingEvents> getByPhone(String phone){ 
		return eventService.getByPhone(phone);
	} 

	@DeleteMapping("delete/deletebooking") 
	public String deleteBookingEvents(int id) { 
		return eventService.deleteBookingEvents(id);
	} 

}
