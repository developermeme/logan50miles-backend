package com.Logan50miles.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.Logan50miles.Entity.BookingTickets;
import com.Logan50miles.Entity.Tickets;
import com.Logan50miles.Service.TicketService;
import com.Logan50miles.Util.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@PostMapping("add/tickets")
	public Tickets addTickets(Tickets t) {
		return ticketService.addTickets(t);
	}

	@PutMapping("update/tickets")
	public Tickets updateTickets(Tickets t) throws ResourceNotFoundException {
		return ticketService.updateTickets(t);
	}

	@DeleteMapping("delete/tickets")
	public String deleteTickets(int id) {
		return ticketService.deleteTickets(id);
	}

	@GetMapping("get/all/tickets")
	public List<Tickets> getAllTickets() {
		return ticketService.getAllTickets();
	}

	@GetMapping("get/tickets/byId")
	public Tickets getTicketsbyId(int id) throws ResourceNotFoundException {
		return ticketService.getTicketsbyId(id);
	}

	@PostMapping("add/bookingtickets")
	public BookingTickets addBookingTickets(BookingTickets bt) throws ResourceNotFoundException, WriterException, IOException {
		return ticketService.addBookingTickets(bt);
	}

	@PutMapping("update/bookingtickets")
	public BookingTickets updateBookingTickets(BookingTickets bt) throws ResourceNotFoundException {
		return ticketService.updateBookingTickets(bt);
	}

	@DeleteMapping("delete/bookingtickets")
	public String deleteBookingTickets(int id) {
		return ticketService.deleteBookingTickets(id);
	}

	@GetMapping("get/all/bookingtickets")
	public List<BookingTickets> getAllBookingTickets() {
		return ticketService.getAllBookingTickets();
	}

	@GetMapping("get/bookingtickets/byId")
	public BookingTickets getBookingTicketsById(int id) throws ResourceNotFoundException {
		return ticketService.getBookingTicketsById(id);
	}
	
	@GetMapping("get/bookingtickets/byEmail")
	public BookingTickets getBookingTicketsByEmail(String email) {
		return ticketService.getBookingTicketsByEmail(email);
	}

}
