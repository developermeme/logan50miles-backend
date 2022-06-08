package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Admin;
import com.Logan50miles.Entity.OtpConfirmationAdmin;

public interface OtpConfirmationAdminRepository extends JpaRepository<OtpConfirmationAdmin, Integer> {

	OtpConfirmationAdmin findByOtp(String otp);

	OtpConfirmationAdmin findByAdmin(Admin adminData);

}
