package com.Logan50miles.Entity;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Swatches {
@Id
private int id;
private HashMap<Integer,String> psize;

public HashMap<Integer, String> getPsize() {
	return psize;
}

public void setPsize(HashMap<Integer, String> psize) {
	this.psize = psize;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

}
