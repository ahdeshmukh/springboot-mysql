package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.DuplicateResourceException;
import com.deshmukhamit.springbootmysql.exception.PasswordException;
import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.model.QUser;
import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private JsonNodeService jsonNodeService;

    @Autowired
    private DateTimeService dateTimeService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers(JsonNode jsonNode) {

        String firstName = (String) jsonNodeService.getValue(jsonNode, "firstName");
        String lastName = (String) jsonNodeService.getValue(jsonNode, "lastName");
        Integer active = (Integer) jsonNodeService.getValue(jsonNode, "active");

        var qUser = QUser.user;
        var query = new JPAQuery(entityManager);
        query.from(qUser);

        if(active != null) {
            query.where(qUser.active.eq(active));
        }
        if(firstName != null) {
            query.where(qUser.firstName.equalsIgnoreCase(firstName));
        }
        if(lastName != null) {
            query.where(qUser.lastName.equalsIgnoreCase(lastName));
        }

        return query.fetch();
        //return userRepository.findAll();
        //
        // return userRepository.findByActiveIs(1); // return only active users
        //

        /*String firstName, lastName;
        Integer active;
        LocalDate createdAt;

        firstName = (String)jsonNodeService.getValue(jsonNode, "firstName");
        lastName = (String)jsonNodeService.getValue(jsonNode, "lastName");
        active = (Integer) jsonNodeService.getValue(jsonNode, "active");
        createdAt = dateTimeService.convertStringToDate((String)jsonNodeService.getValue(jsonNode, "createdAt"));

        /*Specification specification = Specification.where(MySpecification.withEqual("firstName", firstName));
        //specification.and(MySpecification.withEqual("firstName", firstName));
        specification.and(Specification.where(MySpecification.withEqual("lastName", lastName)));
        specification.and(Specification.where(MySpecification.withEqual("active", active)));*/

        // work on pagination
        //return userRepository.findAll(specification, Sort.by("updatedAt").descending());


        /*return userRepository.findAll(Specification.where(MySpecification.withEqual("firstName", firstName))
                .and(MySpecification.withEqual("lastName",lastName))
                .and(MySpecification.withEqual("active", active))
                .and(MySpecification.withDateEqual("createdAt", createdAt)));*/
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

       User existingUserById, updateUser;
        try {
            existingUserById = getUserById(id);
        } catch(ResourceNotFoundException ex) {
            // user with id does not exist, throw ResourceNotFoundException
            throw ex;
        }

        boolean continueUpdate;

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

            Integer active = (user.getActive() == null) ? existingUserById.getActive() : user.getActive();
            updateUser.setActive(active);

            return this.saveUser(updateUser);
        }

        return null; //should never get here, exceptions should be thrown before this
    }

    public User deleteUser(Long id) throws ResourceNotFoundException {
        User user;
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

    private User saveUser(User user) {
        // check if current user is admin or same as the user being updated
        return userRepository.save(user);
    }

}
