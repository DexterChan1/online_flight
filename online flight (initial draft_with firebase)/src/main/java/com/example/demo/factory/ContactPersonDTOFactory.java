package com.example.demo.factory;

import com.example.demo.dto.ContactPersonDTO;
import com.example.demo.model.ContactPerson;
import com.example.demo.model.Trip;
import com.example.demo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactPersonDTOFactory {

    public ContactPersonDTO createContactPersonDto(ContactPerson contactPerson) {
        return ContactPersonDTO.builder()
                .contactPersonId(contactPerson.getContactPersonId())
                .firstName(contactPerson.getFirstName())
                .lastName(contactPerson.getLastName())
                .nationality(contactPerson.getNationality())
                .phoneNumber(contactPerson.getPhoneNumber())
                .emailAddress(contactPerson.getEmailAddress())
                .address(contactPerson.getAddress())
                .tripId(contactPerson.getTrip().getTripId())
                .build();
    }

    public ContactPerson createEntity(ContactPersonDTO contactPersonDTO) {

        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setContactPersonId(contactPersonDTO.getContactPersonId());
        contactPerson.setFirstName(contactPersonDTO.getFirstName());
        contactPerson.setLastName(contactPersonDTO.getLastName());
        contactPerson.setNationality(contactPersonDTO.getNationality());
        contactPerson.setPhoneNumber(contactPersonDTO.getPhoneNumber());
        contactPerson.setEmailAddress(contactPersonDTO.getEmailAddress());
        contactPerson.setAddress(contactPersonDTO.getAddress());
        // Note: For setting the trip relationship, you would usually fetch it from the database
        // using a service or repository, so this step is commented out here.
        // Trip trip = tripService.findById(contactPersonDTO.getTripId());
        // contactPerson.setTrip(trip);
        return contactPerson;
    }
}
