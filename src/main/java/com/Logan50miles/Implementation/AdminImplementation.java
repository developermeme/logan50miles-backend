package com.Logan50miles.Implementation;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Logan50miles.Entity.Admin;
import com.Logan50miles.Entity.OtpConfirmationAdmin;
import com.Logan50miles.Repository.AdminRepository;
import com.Logan50miles.Repository.OtpConfirmationAdminRepository;
import com.Logan50miles.Service.AdminService;
import com.Logan50miles.Util.Mailer;
import com.Logan50miles.Util.ResourceNotFoundException;

@Service
public class AdminImplementation implements AdminService {

	/**/
	@Autowired
    private AdminRepository adminrepository;
	@Autowired
    private OtpConfirmationAdminRepository otpConfirmationAdminRepository;
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    @Override
    public List<Admin> getAllAdmin() {
        return adminrepository.findAll();
    }

    @Override
    public Admin getAdminByEmailId(String emailId) {
        Admin adminData = adminrepository.findByEmailId(emailId);
        return adminData;
    }
    
    @Override
    public ResponseEntity<String> registerationAdmin(Admin admindetails) {
        ResponseEntity<String> result = null;
        Admin adminData = adminrepository.findByEmailId(admindetails.getEmailId());
        if(adminData!=null) {
            result = ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\" : \"Already Registered \"}");
        }else{
            admindetails.setPassword(encoder.encode(admindetails.getPassword()));
            adminrepository.save(admindetails);
            result = ResponseEntity.status(HttpStatus.CREATED).body("{\"message\" : \"Register Succesfully\"}");
        }
        return result;
    }
    
    @Override
    public ResponseEntity<String> loginAdmin(String emailId, String password) {
        ResponseEntity<String> result = null;
        Admin adminData = adminrepository.findByEmailId(emailId);
        if(adminData!=null) {
            if(encoder.matches(password, adminData.getPassword())){
                result = ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"Admin Login Success\", \"userid\" : \""+adminData.getAdminid()+"\", \"emailId\" : \""+adminData.getEmailId()+"\", \"role\" : \""+adminData.getRole()+"\"}");
            }else{
                result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Incorrect password. Try again.\"}");
            }

        }else{
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
        }
        return result;
    }
    
    @Override
    public String deleteAdmin(int id) {
    	adminrepository.deleteById(id);
    	return "Admin Deleted Successfully";
    }
    
    public String updateAdmin(Admin admindetails) throws ResourceNotFoundException {
    	Admin admin = adminrepository.findById(admindetails.getAdminid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
    	if(admindetails.getCity()!=null) {
    		admin.setCity(admindetails.getCity());
    	}
    	else {
    		admin.setCity(admin.getCity());
    	}
    	if(admindetails.getCountry()!=null) {
    		admin.setCountry(admindetails.getCountry());
    	}
    	else {
    		admin.setCountry(admin.getCountry());
    	}
    	if(admindetails.getState()!=null) {
    		admin.setState(admindetails.getState());
    	}
    	else {
    		admin.setState(admin.getState());
    	}
    	if(admindetails.getEmailId()!=null) {
    		admin.setEmailId(admindetails.getEmailId());
    	}
    	else {
    		admin.setEmailId(admin.getEmailId());
    	}
    	if(admindetails.getAdminname()!=null) {
    		admin.setAdminname(admindetails.getAdminname());
    	}
    	else {
    		admin.setAdminname(admin.getAdminname());
    	}
    	if(admindetails.getRole()!=null) {
    		admin.setRole(admindetails.getRole());
    	}
    	else {
    		admin.setRole(admin.getRole());
    	}
    	admin.setPassword(admin.getPassword());
    	admin.setAdminid(admin.getAdminid());
    	adminrepository.save(admin);
    	return "Admin Updated";
    }
    
    @Override
    public ResponseEntity<String> resetAdminPassword(String emailId, String password) throws ParseException {
        ResponseEntity<String> result = null;
        Admin adminData = adminrepository.findByEmailId(emailId);
        if(adminData!=null) {
        	adminData.setPassword(encoder.encode(password));
        	adminrepository.save(adminData);
            result = ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"Reset Sucessfully\"}");

        }else{
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
        }
        return result;
    }
    
    @Override
    public List<Admin> getAllAdminbyRole(String role){
    	return adminrepository.findByRole(role);
    }
    
    @Override
    public ResponseEntity<String> forgotPassword(String emailId) throws ParseException {
        ResponseEntity<String> result = null;
        Admin adminData = adminrepository.findByEmailId(emailId);
        
        if(adminData!=null) {
        	OtpConfirmationAdmin old = otpConfirmationAdminRepository.findByAdmin(adminData);
        	if(old!=null) {
        		otpConfirmationAdminRepository.deleteById(old.getOtpid());
        	}
        	OtpConfirmationAdmin Otp = new OtpConfirmationAdmin(adminData);
        	System.out.println(Otp.getOtp());
        	otpConfirmationAdminRepository.save(Otp);
        	Mailer mail = new Mailer();
        	mail.sendMail("MEMEMOVE Reset Password", "MEMEMOVE verification OTP is:" + Otp.getOtp(), emailId, "no_reply@memebike.tv", "Sal76928");
        	//    verificationOtp(userData.getMobilenumber(), "MEMEMOVE verification OTP is:" + Otp.getOtp());
            result = ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\" : \""+Otp.getOtp()+"\"}");
        }
        else{
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\" : \"Please Register\"}");
        }
        return result;
    }
    
   
    @Override
    public ResponseEntity<String> confirmOtpVerification(String otp) {
        ResponseEntity<String> result;
        OtpConfirmationAdmin otpverify = otpConfirmationAdminRepository.findByOtp(otp);
        if (otpverify != null) {
        	//Admin admin = adminrepository.findById(otpverify.getAdmin().getAdminid()).orElse(null);    
        	//adminrepository.save(admin);
            result = ResponseEntity.status(HttpStatus.ACCEPTED).body("{\"message\" : \"ADMIN\"}");
            otpConfirmationAdminRepository.deleteById(otpverify.getOtpid());

        } else {
            result = ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\" : \"Incorrect Otp\"}");
        }
        return result;
    }
    
    
    
}
