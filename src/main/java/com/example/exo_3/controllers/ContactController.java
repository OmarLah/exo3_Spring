package com.example.exo_3.controllers;

import com.example.exo_3.exceptions.ResourceNotFound;
import com.example.exo_3.models.ContactDTO;
import com.example.exo_3.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("list")
    public String listContacts(Model model) {

        model.addAttribute("contacts", contactService.getcontacts());

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

    @GetMapping("/add")
    public String getContactForm(Model model) {


        model.addAttribute("contact", ContactDTO.builder().build());
        model.addAttribute("mode", "add");

        return "contact/contactForm";
    }

    @PostMapping("/add")
    public String addContact(ContactDTO newContact) {
        contactService.addContact(newContact);

        return "redirect:/contact/list";
    }

}
