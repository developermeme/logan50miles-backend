package com.Logan50miles.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Logan50miles.Repository.ContactRepository;
import com.Logan50miles.Service.ContactService;

@Service
public class ContactImplementation implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	
}
