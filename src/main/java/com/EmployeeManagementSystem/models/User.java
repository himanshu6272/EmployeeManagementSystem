package com.EmployeeManagementSystem.models;

import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
    private String city;
    private String state;
    private String country;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    private List<Role> roles;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    @JsonIgnore
    private PasswordResetToken passwordResetToken;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", roles=" + roles +
                '}';
    }
}
