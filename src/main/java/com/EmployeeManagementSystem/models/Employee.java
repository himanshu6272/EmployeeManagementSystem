package com.EmployeeManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
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
    private String designation;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "State cannot be empty")
    private String state;

    @NotEmpty(message = "Country cannot be empty")
    private String country;

    private String createdBy;
    private String updatedBy;
    private Date createdOn;
    private Date updatedOn;
}
