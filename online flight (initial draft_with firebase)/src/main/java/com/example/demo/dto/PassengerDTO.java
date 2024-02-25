package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PassengerDTO {

    private Long passengerId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String nationality;

    private String passportNumber;

    private Long tripId;  // It's common to represent the relationship with just the ID in DTOs

    // Constructor based on fields if needed
    public PassengerDTO(Long passengerId, String firstName, String lastName,
                        Date dateOfBirth, String nationality, String passportNumber,
                        Long tripId) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.tripId = tripId;
    }

    // You can add more fields or methods if required.

}
