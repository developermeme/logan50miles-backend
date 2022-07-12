package com.Logan50miles.Implementation;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Entity.SuperAdmin;
import com.Logan50miles.Repository.MailConfigurationRepository;
import com.Logan50miles.Repository.SuperAdminRepository;
import com.Logan50miles.Service.SuperAdminService;

@Service
public class SuperAdminImpl implements SuperAdminService {

	@Autowired
	private SuperAdminRepository superAdminRepository;
	@Autowired
	MailConfigurationRepository mailConfigurationRepository;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public ResponseEntity<String> superAdminLogin(String userName, String password) {
		ResponseEntity<String> result;
		SuperAdmin existence = superAdminRepository.findBySupUserName(userName);
		if (existence != null) {
			if (encoder.matches(password, existence.getSupPaswword())) {
				result = ResponseEntity.status(HttpStatus.OK).body("SUPER-ADMIN!");
			} else {
				result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password. Try again.");
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are Not Shop Admin");
		}
		return result;
	}

	@Override
	public ResponseEntity<String> superRegisteration(SuperAdmin superAdmin, MultipartFile file) throws IOException {
		ResponseEntity<String> result;
		SuperAdmin existence = superAdminRepository.findBySupUserName(superAdmin.getSupUserName());
		if (existence != null) {
			result = ResponseEntity.status(HttpStatus.CONFLICT).body("Already Registered");
		} else {
			superAdmin.setSupPaswword(encoder.encode(superAdmin.getSupPaswword()));
			superAdminRepository.save(superAdmin);
			result = ResponseEntity.status(HttpStatus.CREATED).body("registered");
		}
		return result;
	}

	@Override
	public Mailconfiguration addMailconfiguration(Mailconfiguration mailconfiguration) {
		return mailConfigurationRepository.save(mailconfiguration);
	}

	@Override
	public List<Mailconfiguration> getAllMails() {
		return mailConfigurationRepository.findAll();
	}

	@Override
	public String deleteMail(int id) {
		mailConfigurationRepository.deleteById(id);
		return "deleted";
	}
}
