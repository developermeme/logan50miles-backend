package com.Logan50miles.Controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Logan50miles.Entity.Admin;
import com.Logan50miles.Service.AdminService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("Admin")
public class AdminController {

	@Autowired
    private AdminService adminService;

    @GetMapping("get/all/admin")
    public List<Admin> getAllAdmin() {
        return adminService.getAllAdmin();
    }

    @GetMapping("get/Admin/byEmailid")
    public Admin getAdminByEmailId(String emailId) {
        return adminService.getAdminByEmailId(emailId);
    }

    @PostMapping("admin/registeration")
    public ResponseEntity<String> registeration(Admin admin){
        return adminService.registerationAdmin(admin);
    }

    @PostMapping("admin/login")
    public ResponseEntity<String> login(String emailId, String password){
        return adminService.loginAdmin(emailId,password);
    }
	
    @DeleteMapping("delete/admin/byID")
    public String deleteAdmin(int id) {
    	return adminService.deleteAdmin(id);
    }

    @PostMapping("reset/admin/password")
    public ResponseEntity<String> resetAdminPassword(String emailId, String password) throws ParseException {
		return adminService.resetAdminPassword(emailId, password);
	}

    @GetMapping("get/all/admin.byRole")
    public List<Admin> getAllAdminbyRole(String role){
		return adminService.getAllAdminbyRole(role);
	}

    @PostMapping("forgot/password/admin")
    public ResponseEntity<String> forgotPassword(String emailId) throws ParseException {
		return adminService.forgotPassword(emailId);
	}

    @PostMapping("confirm/OTP/verification")
    public ResponseEntity<String> confirmOtpVerification(String otp) {
		return adminService.confirmOtpVerification(otp);
	}
}
