package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FeedComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String comment;
	private String userid;
	private String date;
	@ManyToOne
	@JoinColumn(name = "ppid")
	private PlayerProfile ppid;
	
	public FeedComment() {
		
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PlayerProfile getPpid() {
		return ppid;
	}

	public void setPpid(PlayerProfile ppid) {
		this.ppid = ppid;
	}
	
	
	
}
