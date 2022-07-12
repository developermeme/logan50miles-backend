package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class StateTax {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tid;
	private String stateName;
	private double percentage;
	@ManyToOne
	@JoinColumn(name = "v_id")
	private VendorTax vid;

	public StateTax() {

	}


	public int getTid() {
		return tid;
	}


	public void setTid(int tid) {
		this.tid = tid;
	}


	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public VendorTax getVid() {
		return vid;
	}

	public void setVid(VendorTax vid) {
		this.vid = vid;
	}

}
