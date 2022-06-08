package com.Logan50miles.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Logan50miles.Entity.Contact;
import com.Logan50miles.Service.ContactService;
import com.Logan50miles.Util.ResourceNotFoundException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("Contact")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	@PostMapping("add/contact")
	public Contact addContact(Contact c) {
		return contactService.addContact(c);
	}

	@GetMapping("get/all/contact")
	public List<Contact> getAllContact(){
		return contactService.getAllContact();
	}

	@DeleteMapping("delete/conatct")
	String deleteContact(int id) {
		return contactService.deleteContact(id);
	}

	@PutMapping("update/contact")
	public Contact updateContact(Contact c) throws ResourceNotFoundException {
		return contactService.updateContact(c);
	}

	@GetMapping("get/contact/byId")
	public Contact getContactbyId(int id) throws ResourceNotFoundException {
		return contactService.getContactbyId(id);
	}

	@GetMapping("get/contact/byEmail")
	public List<Contact> getContactbyEmail(String email) {
		return contactService.getContactbyEmail(email);
	}

	@GetMapping("get/contact/byPhone")
	public List<Contact> getContactbyPhone(String phone){
		return contactService.getContactbyPhone(phone);
	}
}
