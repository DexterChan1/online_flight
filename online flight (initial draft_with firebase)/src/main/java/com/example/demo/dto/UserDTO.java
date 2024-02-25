package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String userName;
    private String password;  // Included for operations like registration or login
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String uid;

    @Builder
    public UserDTO(Long userId, String userName, String password, String firstName, String lastName, String email, String phoneNumber, String address, String uid) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.uid = uid;
    }
}