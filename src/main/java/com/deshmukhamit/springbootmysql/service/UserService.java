package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.DuplicateResourceException;
import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {
        // user with email already exists
        if(getUserByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        // TODO: Only self and admin can update a current user. Check the role and throw error on violation

        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if(getUserByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setEmail(user.getEmail());

        return userRepository.save(updateUser);
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.deleteById(id);
        return user;
    }
}
