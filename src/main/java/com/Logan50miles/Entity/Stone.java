package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String stones;
	public Stone() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStones() {
		return stones;
	}
	public void setStones(String stones) {
		this.stones = stones;
	}
	
}
