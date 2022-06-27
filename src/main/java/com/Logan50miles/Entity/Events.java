package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Events {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eid;
	private String title;
	private String date;
	private String details;
	private double entry;
	private boolean status;
	private String url;
	private String location;
	private String time;
	private int noofplayers;
	private int availableplayers;
	private int participatedplayers;
	private String eventtype;
	
	public Events() {
		
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getEntry() {
		return entry;
	}

	public void setEntry(double entry) {
		this.entry = entry;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNoofplayers() {
		return noofplayers;
	}

	public void setNoofplayers(int noofplayers) {
		this.noofplayers = noofplayers;
	}

	public int getAvailableplayers() {
		return availableplayers;
	}

	public void setAvailableplayers(int availableplayers) {
		this.availableplayers = availableplayers;
	}

	public int getParticipatedplayers() {
		return participatedplayers;
	}

	public void setParticipatedplayers(int participatedplayers) {
		this.participatedplayers = participatedplayers;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	
	
}
