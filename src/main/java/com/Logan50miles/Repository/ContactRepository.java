package com.Logan50miles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Logan50miles.Entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
