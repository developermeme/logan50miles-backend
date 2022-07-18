package com.Logan50miles.Service;

import java.io.IOException;
import java.util.List;

import com.Logan50miles.Entity.BookingTickets;
import com.Logan50miles.Entity.Tickets;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.google.zxing.WriterException;

public interface TicketService {

	Tickets addTickets(Tickets t);

	Tickets updateTickets(Tickets t) throws ResourceNotFoundException;

	String deleteTickets(int id);

	List<Tickets> getAllTickets();

	Tickets getTicketsbyId(int id) throws ResourceNotFoundException;

	BookingTickets addBookingTickets(BookingTickets bt) throws ResourceNotFoundException, WriterException, IOException;

	BookingTickets updateBookingTickets(BookingTickets bt) throws ResourceNotFoundException;

	String deleteBookingTickets(int id);

	List<BookingTickets> getAllBookingTickets();

	BookingTickets getBookingTicketsById(int id) throws ResourceNotFoundException;

	BookingTickets getBookingTicketsByEmail(String email);

}
