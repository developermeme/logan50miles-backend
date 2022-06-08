package com.Logan50miles.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Logan50miles.Entity.Contact;
import com.Logan50miles.Repository.ContactRepository;
import com.Logan50miles.Service.ContactService;
import com.Logan50miles.Util.ResourceNotFoundException;

@Service
public class ContactImplementation implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Contact addContact(Contact c) {
		return contactRepository.save(c);
	}
	
	@Override
	public List<Contact> getAllContact(){
		return contactRepository.findAll();
	}
	
	@Override
	public String deleteContact(int id) {
		contactRepository.deleteById(id);
		return "Contact Deleted";
	}
	
	@Override
	public Contact updateContact(Contact c) throws ResourceNotFoundException {
		Contact existance = contactRepository.findById(c.getCid()).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
		existance.setCid(existance.getCid());
		if(c.getFirstname()!=null) {
			existance.setFirstname(c.getFirstname());
		}
		else {
			existance.setFirstname(existance.getFirstname());
		}
		if(c.getLastname()!=null) {
			existance.setLastname(c.getLastname());
		}
		else {
			existance.setLastname(existance.getLastname());
		}
		if(c.getEmailid()!=null) {
			existance.setEmailid(c.getEmailid());
		}
		else {
			existance.setEmailid(existance.getEmailid());
		}
		if(c.getPhone()!=null) {
			existance.setPhone(c.getPhone());
		}
		else {
			existance.setPhone(existance.getPhone());
		}
		if(c.getMessage()!=null) {
			existance.setMessage(c.getMessage());
		}
		else {
			existance.setMessage(existance.getMessage());
		}
		return contactRepository.save(existance);
	}
	
	@Override
	public Contact getContactbyId(int id) throws ResourceNotFoundException {
		return contactRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found"));
	}
	
	@Override
	public List<Contact> getContactbyEmail(String email) {
		return contactRepository.findByEmailid(email);
	}
	
	@Override
	public List<Contact> getContactbyPhone(String phone) {
		return contactRepository.findByPhone(phone);
	}
}
