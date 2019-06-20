package com.deshmukhamit.springbootmysql.controller;

import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api")
public class UserController extends BaseAuthController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers(@RequestBody(required = false) String stringToParse) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(stringToParse);
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
        }

        return userService.getAllUsers(jsonNode);
    }

    // Get user by id
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userService.getUserById(userId);
    }

    // Get user by email
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email) {
        return userService.getUserByEmail(email);
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

    // Update user password
    @PutMapping("/users/{id}/update-password")
    public User updateUserPassword(@PathVariable(value = "id") Long id,
                                     @RequestBody Map<String, String> json) {
        String currentPassword = json.getOrDefault("currentPassword", "");
        String newPassword = json.getOrDefault("newPassword", "");
        String confirmPassword = json.getOrDefault("confirmPassword", "");
        return userService.updatePassword(id, currentPassword, newPassword, confirmPassword);

    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable(value = "id") Long id) {
        return userService.deleteUser(id);
    }

}
