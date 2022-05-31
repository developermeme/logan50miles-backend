package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tickets {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tid;
	private String event;
	private String venue;
	private String date;
	private String tickettype;
	private double price;
	private double servicefee;
	private int nooftickets;
	private int availabletickets;
	private int bookedtickets;
	
	public Tickets() {
		
	}
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTickettype() {
		return tickettype;
	}
	public void setTickettype(String tickettype) {
		this.tickettype = tickettype;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getServicefee() {
		return servicefee;
	}
	public void setServicefee(double servicefee) {
		this.servicefee = servicefee;
	}
	public int getNooftickets() {
		return nooftickets;
	}
	public void setNooftickets(int nooftickets) {
		this.nooftickets = nooftickets;
	}
	public int getAvailabletickets() {
		return availabletickets;
	}
	public void setAvailabletickets(int availabletickets) {
		this.availabletickets = availabletickets;
	}
	public int getBookedtickets() {
		return bookedtickets;
	}
	public void setBookedtickets(int bookedtickets) {
		this.bookedtickets = bookedtickets;
	}
	
	
}
