package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.OtpConfirmation;

public interface OtpConfirmationRepository extends JpaRepository<OtpConfirmation, Integer> {

	OtpConfirmation findByOtp(String otp);

}
