package com.example.exo_3.repositories;

import com.example.exo_3.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findAllByLastnameStartingWith(String value);
}
