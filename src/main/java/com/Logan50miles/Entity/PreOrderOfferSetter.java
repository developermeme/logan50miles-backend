package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PreOrderOfferSetter {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private int offer;
public PreOrderOfferSetter() {
	
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getOffer() {
	return offer;
}
public void setOffer(int offer) {
	this.offer = offer;
}

}
