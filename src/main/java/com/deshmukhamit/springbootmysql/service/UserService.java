package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.DuplicateResourceException;
import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser = userRepository.save(user);
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

            // if a user with existing email is found, make sure that it is == existingUserById,
            // otherwise the provided email belongs to some other user and updating the email to given value should not be allowed
            // or else we will get ConstraintViolation Exception

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

            String password = ((user.getPassword() == null) || (user.getPassword().isBlank())) ? existingUserById.getPassword() : passwordEncoder.encode(user.getPassword());
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

}
