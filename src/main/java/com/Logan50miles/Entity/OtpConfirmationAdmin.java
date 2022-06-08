package com.Logan50miles.Entity;

import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class OtpConfirmationAdmin {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int otpid;
    private String otp;
    private Date createdDate;
    
    @OneToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "adminid")
    private Admin admin;

    public OtpConfirmationAdmin(){
    }
    
    
    public OtpConfirmationAdmin(Admin admin) {
    	this.admin = admin;
    	createdDate = new Date();
    	
    	Random rand =new Random();
    	otp =String.format("%04d", rand.nextInt(10000));
    }


	public int getOtpid() {
		return otpid;
	}


	public void setOtpid(int otpid) {
		this.otpid = otpid;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
    
    
}
