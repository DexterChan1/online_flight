package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.UserDTOFactory;
import com.example.demo.model.User;
import com.example.demo.service.FirebaseAuthService;
import com.example.demo.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDTOFactory userDTOFactory;

    private final FirebaseAuthService firebaseAuthService;

    @Autowired
    public UserController(UserService userService, UserDTOFactory userDTOFactory, FirebaseAuthService firebaseAuthService) {
        this.userService = userService;
        this.userDTOFactory = userDTOFactory;
        this.firebaseAuthService = firebaseAuthService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(userDTOFactory::createUserDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.success(userDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDTO userDTO = userDTOFactory.createUserDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.success(userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.fail("User not found"));
        }
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<User> getUserByUid(@PathVariable String uid) {
        User user = userService.getUserByUid(uid);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@RequestBody @Valid UserDTO userDTO) throws FirebaseAuthException {
        User user = userDTOFactory.createEntity(userDTO); // Convert DTO to User entity

        // Use FirebaseAuth to register the user and get the external user ID
        String externalUserId = firebaseAuthService.registerUser(userDTO.getEmail(), userDTO.getPassword());

        // Set the external user ID to the user object
        user.setUid(externalUserId);  // Note the method name change to match the User entity

        // Save the user using the service
        User createdUser = userService.createUser(user);  // Assuming userService handles User entities

        // Convert the created user entity back to DTO
        UserDTO createdUserDTO = userDTOFactory.createUserDto(createdUser);  // Convert entity back to DTO

        // Return the response
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.success(createdUserDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        try {
            User user = userDTOFactory.createEntity(userDTO);
            User updatedUser = userService.updateUser(id, user);
            UserDTO updatedUserDTO = userDTOFactory.createUserDto(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.success(updatedUserDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.fail("User not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.success(null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.fail("User not found"));
        }
    }
}
