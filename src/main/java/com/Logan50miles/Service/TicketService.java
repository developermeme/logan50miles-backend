package com.Logan50miles.Service;

import java.util.List;

import com.Logan50miles.Entity.BookingTickets;
import com.Logan50miles.Entity.Tickets;
import com.Logan50miles.Util.ResourceNotFoundException;

public interface TicketService {

	Tickets addTickets(Tickets t);

	Tickets updateTickets(Tickets t) throws ResourceNotFoundException;

	String deleteTickets(int id);

	List<Tickets> getAllTickets();

	Tickets getTicketsbyId(int id) throws ResourceNotFoundException;

	BookingTickets addBookingTickets(BookingTickets bt) throws ResourceNotFoundException;

	BookingTickets updateBookingTickets(BookingTickets bt) throws ResourceNotFoundException;

	String deleteBookingTickets(int id);

	List<BookingTickets> getAllBookingTickets();

	BookingTickets getBookingTicketsById(int id) throws ResourceNotFoundException;

}