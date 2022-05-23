package com.Logan50miles.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.User;
import com.Logan50miles.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("user/login") 
	public ResponseEntity<String> userLogin(String email, String password) throws ParseException{
		return userService.userLogin(email, password);
	}
	
	@PostMapping("user/registration") 
	public ResponseEntity<String> userRegistration(User user, MultipartFile file) throws IOException, ParseException{ 
		return userService.userRegistration(user, file);
	}
	
	@PostMapping("confirmation") 
	public ResponseEntity<String> confirmRegistration(String confirmationToken){ 
		return userService.confirmRegistration(confirmationToken);
	}
	
	@PostMapping("forgotpassword") 
	public ResponseEntity<String> forgorPassword(String email) throws ParseException { 
		return userService.forgotPassword(email);
	}
	@PostMapping("updatepassword") 
	public ResponseEntity<String> updatePassword(String email, String password) throws ParseException { 
		return userService.updatePassword(email, password); 
	}
	
	@PostMapping("updateuser") 
	public String updateUser(User user, MultipartFile file) throws IOException{ 
		return userService.updateUser(user, file); 
	} 
	
	@GetMapping("get/users") 
	public List<User> getAllUser(){ 
		return userService.getAllUser(); 
	} 
	
	@GetMapping("get/user/byId") 
	public User getUserById(int userId) {
		return userService.getUserById(userId);
	}

	@GetMapping("get/user/byEmail") 
	public User getUserByEmailId(String email) {
		return userService.getUserByEmailId(email);
	}

	@DeleteMapping("delete/user/byId")
	public String deleteUser(int id) {
		return userService.deleteUser(id);
	}
}
