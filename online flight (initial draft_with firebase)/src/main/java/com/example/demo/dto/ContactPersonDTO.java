package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactPersonDTO {

    private Long contactPersonId;
    private String firstName;
    private String lastName;
    private String nationality;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private Long tripId; // Assuming you only need the tripId for referencing

    // Constructor based on fields if needed
    public ContactPersonDTO(Long contactPersonId, String firstName, String lastName,
                            String nationality, String phoneNumber,String emailAddress, String address,
                            Long tripId) {
        this.contactPersonId = contactPersonId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.tripId = tripId;
    }

    // You can add more fields or methods if required.
}
