package com.Logan50miles.Service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Logan50miles.Entity.Admin;

public interface AdminService {

	List<Admin> getAllAdmin();

	Admin getAdminByEmailId(String emailId);

	ResponseEntity<String> registerationAdmin(Admin admindetails);

	ResponseEntity<String> loginAdmin(String emailId, String password);

	String deleteAdmin(int id);

	ResponseEntity<String> resetAdminPassword(String emailId, String password) throws ParseException;

	List<Admin> getAllAdminbyRole(String role);

	ResponseEntity<String> forgotPassword(String emailId) throws ParseException;

	ResponseEntity<String> confirmOtpVerification(String otp);


}
