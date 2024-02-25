package com.example.demo.factory;



import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOFactory {

    public UserDTO createUserDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
               // .password(user.getPassword()) // Be cautious when setting this, especially for response purposes.
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .uid(user.getUid())
                .build();
    }

    public User createEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
     //   user.setPassword(userDTO.getPassword()); // Ensure to hash before saving to the database.
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setUid(userDTO.getUid());
        return user;
    }
}

