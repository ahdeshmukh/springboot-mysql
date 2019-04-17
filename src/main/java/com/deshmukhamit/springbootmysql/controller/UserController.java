package com.deshmukhamit.springbootmysql.controller;

import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController extends BaseAuthController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by id
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    // Get user by email
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    // Create a new User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    // Update a user
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userData) {
        return userService.updateUser(id, userData);
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable(value = "id") Long id) {
        return userService.deleteUser(id);
    }

}


/*
*
* // Get user by id
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    // Get user by email
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    // Create a new User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    // Update a User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userData) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());

        User updatedUser = userRepository.save(user);
        return updatedUser;

    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.deleteById(userId);

        return user;
    }
*
* */
