package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ContactPersonDTO;
import com.example.demo.model.ContactPerson;
import com.example.demo.service.ContactPersonService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contact-persons")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    @Autowired
    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ContactPersonDTO>>> getAllContactPersons() {
        List<ContactPersonDTO> contactPersons = contactPersonService.getAllContactPersons();
        return ResponseEntity.ok(ApiResponseDTO.success(contactPersons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ContactPersonDTO>> getContactPersonById(@PathVariable Long id) {
        ContactPersonDTO contactPerson = contactPersonService.getContactPersonById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(contactPerson));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ContactPersonDTO>> createContactPerson(@Valid @RequestBody ContactPersonDTO contactPersonDTO) {
        ContactPersonDTO createdContactPerson = contactPersonService.createContactPerson(contactPersonDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(createdContactPerson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ContactPersonDTO>> updateContactPerson(@PathVariable Long id, @Valid @RequestBody ContactPersonDTO contactPersonDTO) {
        ContactPersonDTO updatedContactPerson = contactPersonService.updateContactPerson(id, contactPersonDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedContactPerson));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteContactPerson(@PathVariable Long id) {
        contactPersonService.deleteContactPerson(id);
        return ResponseEntity.noContent().build();
    }
    /*@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteContactPerson(@PathVariable Long id) {
        contactPersonService.deleteContactPerson(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Contact person deleted successfully."));
    } */
}
