package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	List<Contact> findByEmailid(String email);

	List<Contact> findByPhone(String phone);

}
