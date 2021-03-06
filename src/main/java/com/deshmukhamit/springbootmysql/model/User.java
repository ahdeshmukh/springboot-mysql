package com.deshmukhamit.springbootmysql.model;

//import com.deshmukhamit.springbootmysql.annotation.PasswordRequired;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class) // why AudtingEntityListener is needed (https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}) -> using @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) instead

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    @NotEmpty(message = "First Name is required")
    private String firstName;

    @Column(nullable = false, length = 40)
    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @Column(nullable = false, length = 60, unique = true)
    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be in a valid format")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@NotEmpty(message = "Password is required")
    //@PasswordRequired(message = "Custom password is required")
    private String password;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createdAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updatedAt = new Date();

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer active = 1;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Todo: move password encryption here eventually
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
