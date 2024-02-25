package com.example.demo.service;


import com.example.demo.dto.UserDTO;
import com.example.demo.factory.UserDTOFactory;
import com.example.demo.model.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) throws ResourceNotFoundException {
        User existingUser = getUserById(id);

        if (existingUser != null) {
            existingUser.setUserName(updatedUser.getUserName());
      //      existingUser.setPassword(updatedUser.getPassword());  // Remember to securely hash the password before saving
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());

            // Save the updated user
            return userRepository.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User", "id", id);
        }
    }

    public void deleteUserById(Long id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }
}