package com.example.exo_3.services;

import com.example.exo_3.models.ContactDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
public class ContactService {
    private final Map<UUID, ContactDTO> contacts;

    public ContactService() {
        this.contacts = new HashMap<>();

        ContactDTO contactA = ContactDTO.builder()
                .id(UUID.randomUUID())
                .lastname("Doe")
                .firstname("John")
                .age(21)
                .build();

        ContactDTO contactB = ContactDTO.builder()
                .id(UUID.randomUUID())
                .lastname("Dupond")
                .firstname("Franck")
                .age(35)
                .build();

        ContactDTO contactC = ContactDTO.builder()
                .id(UUID.randomUUID())
                .lastname("Martin")
                .firstname("Marie")
                .age(18)
                .build();

        contacts.put(contactA.getId(), contactA);
        contacts.put(contactB.getId(), contactB);
        contacts.put(contactC.getId(), contactC);
    }

    public List<ContactDTO> getcontacts() {
        return contacts.values().stream().toList();
    }

    public Optional<ContactDTO> getcontactById(UUID id) {
        return contacts.values().stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public ContactDTO addContact(ContactDTO contactData) {
        if (contactData.getId() == null) {
            contactData.setId(UUID.randomUUID());
        }

        contacts.put(contactData.getId(), contactData);

        return contactData;
    }

    public Boolean deleteContactById(UUID id) {
        Optional<ContactDTO> foundContact = getcontactById(id);

        if(foundContact.isPresent()) {
            contacts.remove(foundContact.get().getId());

            return true;
        }

        return false;
    }

    public ContactDTO editContact(UUID id, ContactDTO newDatas) {
        AtomicReference<ContactDTO> atomicReference = new AtomicReference<>();

        Optional<ContactDTO> foundContact = getcontactById(id);

        foundContact.ifPresentOrElse(found -> {
            if (newDatas.getLastname() != null) {
                found.setLastname(newDatas.getLastname());
            }

            if (newDatas.getFirstname() != null) {
                found.setFirstname(newDatas.getFirstname());
            }

            if (newDatas.getAge() != 0) {
                found.setAge(newDatas.getAge());
            }

            atomicReference.set(found);
        }, () -> {
            atomicReference.set(null);
        });

        return atomicReference.get();
    }

}
