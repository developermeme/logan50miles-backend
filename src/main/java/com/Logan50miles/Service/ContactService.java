package com.Logan50miles.Service;

import java.util.List;

import com.Logan50miles.Entity.Contact;
import com.Logan50miles.Util.ResourceNotFoundException;

public interface ContactService {

	Contact addContact(Contact c);

	List<Contact> getAllContact();

	String deleteContact(int id);

	Contact updateContact(Contact c) throws ResourceNotFoundException;

	Contact getContactbyId(int id) throws ResourceNotFoundException;

	List<Contact> getContactbyEmail(String email);

	List<Contact> getContactbyPhone(String phone);

}
