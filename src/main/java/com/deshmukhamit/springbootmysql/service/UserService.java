package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.DuplicateResourceException;
import com.deshmukhamit.springbootmysql.exception.PasswordException;
import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User getUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    public User addUser(User user) {
        User newUser = null;

        try {
            User existingUser = getUserByEmail(user.getEmail());
            if(existingUser.getId() > 0) {
                throw new DuplicateResourceException("User", "email", user.getEmail());
            }
        } catch (ResourceNotFoundException ex) {
            // this is good, since a user with the email does not exist, so we can create a record with the given email
            // encode the password before storing it in the database
            user.setPassword(passwordService.encodePassword(user.getPassword()));
            newUser = this.saveUser(user);
        }

        return newUser;
    }

    public User updateUser(Long id, User user) throws ResourceNotFoundException, DuplicateResourceException {
        // TODO: Only self and admin can update a current user. Check the role and throw error on violation

       User existingUserById, updateUser = null;
        try {
            existingUserById = getUserById(id);
        } catch(ResourceNotFoundException ex) {
            // user with id does not exist, throw ResourceNotFoundException
            throw ex;
        }

        Boolean continueUpdate = false;

        try {

            // if a user with existing email is found, make sure that user's id == existingUserById,
            // otherwise the provided email belongs to some other user and updating the email to the given value should not be allowed
            //  not doing so will throw ConstraintViolation Exception

            User existingUserByEmail = getUserByEmail(user.getEmail());
            if(!(existingUserByEmail.getId().equals(existingUserById.getId()))) {
                throw new DuplicateResourceException("User", "email", user.getEmail());
            }

            continueUpdate = true;

        } catch(ResourceNotFoundException ex) {
            // good -> this email is not taken, so continue
            continueUpdate = true;
        }

        if(continueUpdate) {
            updateUser = new User();
            updateUser.setId(id);
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEmail(user.getEmail());

            String password = ((user.getPassword() == null) || (user.getPassword().isBlank())) ? existingUserById.getPassword() : passwordService.encodePassword(user.getPassword());
            updateUser.setPassword(password);

            return userRepository.save(updateUser);
        }

        return null; //should never get here, exceptions should be thrown before this
    }

    public User deleteUser(Long id) throws ResourceNotFoundException {
        User user = null;
        try {
            user = getUserById(id);
            userRepository.deleteById(id);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }

        return user;
    }

    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) throws PasswordException {
        if(currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
            throw new PasswordException("Values cannot be blank");
        }
        if(!newPassword.equals(confirmPassword)) {
            //return null;
            throw new PasswordException("New Password and Confirm Password must match");
        }

        // TODO: check if the currentPassword is correct, if not return
        User user = this.getUserById(id);
        if(!passwordService.verifyPassword(currentPassword, user.getPassword())) {
            throw new PasswordException("Current password is not valid");
        }
        user.setPassword(passwordService.encodePassword(newPassword));

        return this.saveUser(user);
    }

    public User saveUser(User user) {
        // check if current user is admin or same as the user being updated
        return userRepository.save(user);
    }

}
