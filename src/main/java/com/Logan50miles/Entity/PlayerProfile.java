package com.Logan50miles.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class PlayerProfile {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ppid;
	private String feedurl;
	private int like;
	private String description;
	private int dislike;
	private int pid;
	@OneToMany
	private List<FeedComment> comment;
	 
	public PlayerProfile() {
		
	}

	public int getPpid() {
		return ppid;
	}

	public void setPpid(int ppid) {
		this.ppid = ppid;
	}

	public String getFeedurl() {
		return feedurl;
	}

	public void setFeedurl(String feedurl) {
		this.feedurl = feedurl;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}

	public int getPid() {
		return pid;
	}

	public void setProid(int pid) {
		this.pid = pid;
	}

	public List<FeedComment> getComment() {
		return comment;
	}

	public void setComment(List<FeedComment> comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
