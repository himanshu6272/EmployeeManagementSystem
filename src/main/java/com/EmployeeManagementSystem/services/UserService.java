package com.EmployeeManagementSystem.services;

import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import com.EmployeeManagementSystem.models.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    boolean userExist(String email);

    User getUserById(int id);

    void deleteUserById(int id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    PasswordResetToken saveToken(PasswordResetToken token);

    PasswordResetToken getByOtp(String otp);

    void deleteToken(int id);
}
