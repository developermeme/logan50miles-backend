package com.Logan50miles.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Referral {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int refId;
	private String cusId;
	private String refCod;
	private int refCount;
	private double refAmount;

	public Referral() {
		
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getRefCod() {
		return refCod;
	}

	public void setRefCod(String refCod) {
		this.refCod = refCod;
	}

	public int getRefCount() {
		return refCount;
	}

	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}

	public double getRefAmount() {
		return refAmount;
	}

	public void setRefAmount(double refAmount) {
		this.refAmount = refAmount;
	}
	
}
