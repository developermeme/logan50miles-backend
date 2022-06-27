package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.OtpConfirmationPlayer;
import com.Logan50miles.Entity.Professionals;

public interface OtpConfirmationPlayerRepository extends JpaRepository<OtpConfirmationPlayer, Integer>{

	OtpConfirmationPlayer findByProf(Professionals playerData);

	OtpConfirmationPlayer findByOtp(String otp);

}
