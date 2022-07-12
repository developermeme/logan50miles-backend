package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.VendorOtp;

public interface VendorOtpRepository extends JpaRepository<VendorOtp, Integer>{
	    @Query("from VendorOtp where otp=:otp")
		VendorOtp findByOtp(String otp);
}
