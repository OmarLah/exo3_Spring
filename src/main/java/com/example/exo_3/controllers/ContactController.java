package com.example.exo_3.controllers;

import com.example.exo_3.exceptions.ResourceNotFound;
import com.example.exo_3.models.ContactDTO;
import com.example.exo_3.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("list")
    public String listContacts(Model model, @RequestParam(value = "lastname", defaultValue = "") String value) {

        List<ContactDTO> contacts = contactService.getcontacts();
        if (!value.isEmpty() && !value.isBlank()) {
            contacts = contacts.stream().filter(c -> c.getLastname().startsWith(value)).toList();
        }

        model.addAttribute("contacts", contacts);

        return "contact/list";
    }

    @GetMapping("details/{contactId}")
    public String getContactDetails(@PathVariable("contactId") UUID id, Model model) {
        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if (foundContact.isPresent()) {
            model.addAttribute("contact", foundContact.get());
            model.addAttribute("mode", "details");

            return "contact/contactForm";
        }

        throw new ResourceNotFound();
    }

    @GetMapping("add")
    public String getContactForm(Model model) {


        model.addAttribute("contact", ContactDTO.builder().build());
        model.addAttribute("mode", "add");

        return "contact/contactForm";
    }

    @PostMapping("add")
    public String addContact(ContactDTO newContact) {
        contactService.addContact(newContact);

        return "redirect:/contact/list";
    }

    @GetMapping("delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") UUID id) {

        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if(foundContact.isPresent()) {
            contactService.deleteContactById(foundContact.get().getId());
            return "redirect:/contact/list";
        }

        throw new ResourceNotFound();
    }

    @GetMapping("edit/{contactId}")
    public String editContact(@PathVariable("contactId") UUID id, Model model) {

        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if (foundContact.isPresent()) {
            model.addAttribute("contact", foundContact.get());
            model.addAttribute("mode", "edit");

            return "contact/contactForm";
        }

        throw new ResourceNotFound();
    }

    @PostMapping("edit/{contactId}")
    public String editPost(@PathVariable("contactId") UUID id, Model model) {
        Optional<ContactDTO> foundContact = contactService.getcontactById(id);

        if (foundContact.isPresent()) {
            model.addAttribute("contact", foundContact.get());
            model.addAttribute("mode", "edit");

            return "contact/list";
        }

        throw new ResourceNotFound();
    }

}
