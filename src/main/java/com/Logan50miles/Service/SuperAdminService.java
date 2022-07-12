package com.Logan50miles.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Entity.SuperAdmin;

public interface SuperAdminService {

	ResponseEntity<String> superAdminLogin(String userName, String password);

	ResponseEntity<String> superRegisteration(SuperAdmin superAdmin, MultipartFile file) throws IOException;

	List<Mailconfiguration> getAllMails();

	Mailconfiguration addMailconfiguration(Mailconfiguration mailconfiguration);

	String deleteMail(int id);

}
