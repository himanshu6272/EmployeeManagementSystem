package com.EmployeeManagementSystem.models;

import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotEmpty
    @Size(min = 4, max = 10, message = "Firstname should be minimum 4 and maximum 10 character long")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 10, message = "Lastname should be minimum 2 and maximum 10 character long")
    private String lastName;
    @NotEmpty
    @Size(min = 10, max = 10, message = "Mobile number is not valid")
    private String mobile;

    @NotEmpty
    @Email(message = "Email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Password at least 8 characters long and must contain digit, uppercase alphabet and lowercase alphabet")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password at least 8 characters long and must contain a digit, upper case alphabet and lowercase alphabet")
    private String password;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "State cannot be empty")
    private String state;

    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
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
