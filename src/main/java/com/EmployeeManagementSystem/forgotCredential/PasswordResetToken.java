package com.EmployeeManagementSystem.forgotCredential;

import com.EmployeeManagementSystem.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "passwordResetToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String otp;
    private Date expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;
}
