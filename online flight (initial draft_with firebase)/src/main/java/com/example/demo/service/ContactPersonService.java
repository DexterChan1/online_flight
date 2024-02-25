package com.example.demo.service;

import com.example.demo.dto.ContactPersonDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.ContactPersonDTOFactory;
import com.example.demo.model.ContactPerson;
import com.example.demo.model.Trip;
import com.example.demo.repository.ContactPersonRepository;
import com.example.demo.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactPersonService {


    private ContactPersonRepository contactPersonRepository;


    private TripRepository tripRepository;


    private ContactPersonDTOFactory contactPersonDTOFactory;

    @Autowired
    public ContactPersonService(ContactPersonRepository contactPersonRepository, TripRepository tripRepository, ContactPersonDTOFactory contactPersonDTOFactory) {
        this.contactPersonRepository = contactPersonRepository;
        this.tripRepository = tripRepository;
        this.contactPersonDTOFactory = contactPersonDTOFactory;
    }

    public List<ContactPersonDTO> getAllContactPersons() {
        return contactPersonRepository.findAll()
                .stream()
                .map(contactPersonDTOFactory::createContactPersonDto)
                .collect(Collectors.toList());
    }

    public ContactPersonDTO getContactPersonById(Long contactPersonId) {
        ContactPerson contactPerson = contactPersonRepository.findById(contactPersonId)
                .orElseThrow(() -> new ResourceNotFoundException("ContactPerson", "id", contactPersonId));
        return contactPersonDTOFactory.createContactPersonDto(contactPerson);
    }

    public ContactPersonDTO createContactPerson(ContactPersonDTO contactPersonDTO) {
        ContactPerson contactPerson = contactPersonDTOFactory.createEntity(contactPersonDTO);

        // Setting the associated Trip for the ContactPerson
        Trip trip = tripRepository.findById(contactPersonDTO.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", contactPersonDTO.getTripId()));
        contactPerson.setTrip(trip);

        ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);
        return contactPersonDTOFactory.createContactPersonDto(savedContactPerson);
    }

    public ContactPersonDTO updateContactPerson(Long contactPersonId, ContactPersonDTO contactPersonDTO) {
        ContactPerson existingContactPerson = contactPersonRepository.findById(contactPersonId)
                .orElseThrow(() -> new ResourceNotFoundException("ContactPerson", "id", contactPersonId));

        ContactPerson contactPersonToUpdate = contactPersonDTOFactory.createEntity(contactPersonDTO);
        contactPersonToUpdate.setContactPersonId(contactPersonId); // Ensure the ID is retained

        // Setting the associated Trip for the ContactPerson
        Trip trip = tripRepository.findById(contactPersonDTO.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", contactPersonDTO.getTripId()));
        contactPersonToUpdate.setTrip(trip);

        ContactPerson updatedContactPerson = contactPersonRepository.save(contactPersonToUpdate);
        return contactPersonDTOFactory.createContactPersonDto(updatedContactPerson);
    }

    public void deleteContactPerson(Long contactPersonId) {
        if (!contactPersonRepository.existsById(contactPersonId)) {
            throw new ResourceNotFoundException("ContactPerson", "id", contactPersonId);
        }
        contactPersonRepository.deleteById(contactPersonId);
    }
}