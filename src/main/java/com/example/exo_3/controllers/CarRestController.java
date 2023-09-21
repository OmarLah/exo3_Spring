package com.example.exo_3.controllers;

import com.example.exo_3.exceptions.ResourceNotFound;
import com.example.exo_3.models.ContactDTO;
import com.example.exo_3.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contacts")
@Slf4j
public class CarRestController {

    private final ContactService contactService;

    @GetMapping
    public List<ContactDTO> listContacts(@RequestParam(value = "name", defaultValue = "") String filterByName) {

        List<ContactDTO> contacts = contactService.getcontacts();

        if(!filterByName.isEmpty() && !filterByName.isBlank()) {
            contacts = contacts.stream().filter(c -> c.getLastname().startsWith(filterByName)).toList();
        }

        return contacts;
    }

    @GetMapping("/{contactId}")
    public ContactDTO getDetailsContact(@PathVariable("contactId") UUID id)  {

        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if(foundContact.isPresent()) {
            return foundContact.get();
        }
        throw new ResourceNotFound();
    }



    @PostMapping("/add")
    public ResponseEntity<String> addDogHandler(@RequestBody ContactDTO newContact) {
        ContactDTO createdContact = contactService.addContact(newContact);

        return new ResponseEntity<String>("Contact created! New id: " + createdContact.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<String> deleteContactHandler(@PathVariable("contactId") UUID id) {

        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if(foundContact.isPresent()) {

            if(contactService.deleteContactById(id)) {
                return new ResponseEntity<String>("Contact delete at id : " + id, HttpStatus.OK);
            }

            return new ResponseEntity<String>("Somthing went wrong...", HttpStatus.NOT_MODIFIED);

        } else {
            throw new ResourceNotFound();
        }
    }

    @PatchMapping("/{contactId}")
    public ContactDTO editContact(@PathVariable("contactId") UUID id, @RequestBody ContactDTO contactData) {
        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if (foundContact.isPresent()) {
            return contactService.editContact(id, contactData);
        } else {
            throw new ResourceNotFound();
        }
    }



}
