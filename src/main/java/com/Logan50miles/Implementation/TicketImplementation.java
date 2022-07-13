package com.Logan50miles.Implementation;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Logan50miles.Entity.BookingTickets;
import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Entity.Tickets;
import com.Logan50miles.Repository.BookingTicketsRepository;
import com.Logan50miles.Repository.MailConfigurationRepository;
import com.Logan50miles.Repository.TicketsRepository;
import com.Logan50miles.Service.TicketService;
import com.Logan50miles.Util.Mailer;
import com.Logan50miles.Util.QRCodeGenerator;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.google.zxing.WriterException;

@Service
public class TicketImplementation implements TicketService {

	@Autowired 
	private TicketsRepository ticketsRepository;
	@Autowired
	private BookingTicketsRepository bookingTicketsRepository;
	@Autowired
	private MailConfigurationRepository mailConfigurationRepository;
	
	
	String image = null;
	@Override
	public Tickets addTickets(Tickets t) {
		t.setAvailabletickets(t.getNooftickets());
		t.setBookedtickets(0);
		return ticketsRepository.save(t);
	}
	
	@Override
	public Tickets updateTickets(Tickets t) throws ResourceNotFoundException {
		Tickets existance = ticketsRepository.findById(t.getTid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		existance.setTid(existance.getTid());
		if(t.getEvent()!=null) {
			existance.setEvent(t.getEvent());
		}
		else {
			existance.setEvent(existance.getEvent());
		}
		if(t.getVenue()!=null) {
			existance.setVenue(t.getVenue());
		}
		else {
			existance.setVenue(existance.getVenue());
		}
		if(t.getDate()!=null) {
			existance.setDate(t.getDate());
		}
		else {
			existance.setDate(existance.getDate());
		}
		if(t.getTickettype()!=null) {
			existance.setTickettype(t.getTickettype());
		}
		else {
			existance.setTickettype(existance.getTickettype());
		}
		if(t.getPrice()!=0) {
			existance.setPrice(t.getPrice());
		}
		else {
			existance.setPrice(existance.getPrice());
		}
		if(t.getServicefee()!=0) {
			existance.setServicefee(t.getServicefee());
		}
		else {
			existance.setServicefee(existance.getServicefee());
		}
		if(t.getNooftickets()!=0) {
			existance.setNooftickets(t.getNooftickets());
		}
		else {
			existance.setNooftickets(existance.getNooftickets());
		}
		if(t.getAvailabletickets()!=0) {
			existance.setAvailabletickets(t.getAvailabletickets());
		}
		else {
			existance.setAvailabletickets(existance.getAvailabletickets());
		}
		if(t.getBookedtickets()!=0) {
			existance.setBookedtickets(t.getBookedtickets());
		}
		else {
			existance.setBookedtickets(existance.getBookedtickets());
		}
		return ticketsRepository.save(existance);
	}
	
	@Override
	public String deleteTickets(int id) {
		ticketsRepository.deleteById(id);
		return "Ticket Deleted";
	}
	
	@Override
	public List<Tickets> getAllTickets() {
		return ticketsRepository.findAll();	
	}
	
	@Override
	public Tickets getTicketsbyId(int id) throws ResourceNotFoundException {
		return ticketsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
	}
	
	@Override
	public BookingTickets addBookingTickets(BookingTickets bt) throws ResourceNotFoundException, WriterException, IOException {
		Tickets t = ticketsRepository.findById(bt.getTicketid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		t.setAvailabletickets(t.getAvailabletickets()-bt.getQuantity());
		t.setBookedtickets(t.getBookedtickets()+bt.getQuantity());
		ticketsRepository.save(t);
		image = QRCodeGenerator.getQRCodeImage(String.valueOf(bt.getBid()),250,250);
		
		String text =
				"<div style=\"border:1px solid black; padding:10px ; text-align:center;\">"+
				"<h4><u>"+t.getEvent()+" Tickets Information:</u></h4>"+
				image+
				"<h5>Email: "+bt.getUseremail()+"</h5>"+
				"<h5>No of Tickets: "+bt.getQuantity()+"</h5>"+
				"<h5>Venue: "+t.getVenue()+"</h5>"+
				"<h5>Stand: "+t.getTickettype()+"</h5>"+
				"<h5>Date: "+t.getDate()+"</h5>"+
				"<h5>Amount Paid"+bt.getTotal()+"</h5>"+
				"</div>";
		Mailconfiguration m = mailConfigurationRepository.findAll().stream().filter(x -> x.getType().equals("general"))
				.findAny().orElse(null);
		Mailer mail = new Mailer();
    	mail.sendMail("LOGAN50MILES TICKET CONFIRMATION", text , bt.getUseremail(), m.getEmail(), m.getPassword());   	
		return bookingTicketsRepository.save(bt);
	}
	
	@Override
	public BookingTickets updateBookingTickets(BookingTickets bt) throws ResourceNotFoundException {
		BookingTickets existance = bookingTicketsRepository.findById(bt.getBid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		existance.setBid(existance.getBid());
		if(bt.getUseremail()!=null) {
			existance.setUseremail(bt.getUseremail());
		}
		else {
			existance.setUseremail(existance.getUseremail());
		}
		if(bt.getTicketid()!=0){
			existance.setTicketid(bt.getTicketid());
		}
		else {
			existance.setTicketid(existance.getTicketid());
		}
		if(bt.getQuantity()!=0) {
			existance.setQuantity(bt.getQuantity());
		}
		else {
			existance.setQuantity(existance.getQuantity());
		}
		if(bt.getTotal()!=0) {
			existance.setTotal(bt.getTotal());
		}
		else {
			existance.setTotal(existance.getTotal());
		}
		if(bt.getServicefee()!=0) {
			existance.setServicefee(bt.getServicefee());
		}
		else {
			existance.setServicefee(existance.getServicefee());
		}
		if(bt.getSubtotal()!=0) {
			existance.setSubtotal(bt.getSubtotal());
		}
		else {
			existance.setSubtotal(existance.getSubtotal());
		}
		return bookingTicketsRepository.save(existance);
	}
	
	@Override
	public String deleteBookingTickets(int id) {
		bookingTicketsRepository.deleteById(id);
		return "Booking Tickets Deleted";
	}
	
	@Override
	public List<BookingTickets> getAllBookingTickets() {
		return bookingTicketsRepository.findAll();
	}
	
	@Override
	public BookingTickets getBookingTicketsById(int id) throws ResourceNotFoundException {
		return bookingTicketsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
	}

}
