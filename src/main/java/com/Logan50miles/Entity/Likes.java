package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Likes {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer likeid;
    private String userId;
    private int ppid;
    private String type;
    public Likes() {
    	
    }
	public Integer getLikeid() {
		return likeid;
	}
	public void setLikeid(Integer likeid) {
		this.likeid = likeid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
    
}
