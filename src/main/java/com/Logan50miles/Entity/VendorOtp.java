package com.Logan50miles.Entity;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VendorOtp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int vid;
	private String shopName;
	private String otp;
	
	public VendorOtp() {
		
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		Random rand = new Random();
		otp =String.format("%04d", rand.nextInt(10000));
		this.otp = otp;
	}
	
}
