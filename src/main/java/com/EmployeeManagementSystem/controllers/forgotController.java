package com.EmployeeManagementSystem.controllers;

import com.EmployeeManagementSystem.exceptions.ApiResponse;
import com.EmployeeManagementSystem.forgotCredential.ForgotPasswordRequest;
import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import com.EmployeeManagementSystem.forgotCredential.ResetPasswordRequest;
import com.EmployeeManagementSystem.models.User;
import com.EmployeeManagementSystem.services.EmailService;
import com.EmployeeManagementSystem.services.UserService;
import com.EmployeeManagementSystem.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/forgot")
public class forgotController {
    @Autowired
    UserService userService;
    //    @Autowired
//    PasswordResetTokenRepository tokenRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    OTPGenerator otpGenerator;

    @PostMapping("/forgotPassword")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String otp = this.otpGenerator.generateOTP();
        if (this.userService.userExist(request.getEmail())) {
            User user = this.userService.getUserByEmail(request.getEmail());
            this.userService.deleteToken(user.getId());
            PasswordResetToken token = new PasswordResetToken();
            token.setOtp(otp);
            token.setUser(user);
            token.setExpiryDate(new Date(System.currentTimeMillis() + 600000));
            PasswordResetToken createdToken = this.userService.saveToken(token);
            this.emailService.sendEmail(user.getEmail(), "Password Reset OTP", "Dear " + user.getFirstName() + ",\n\nYour OTP is: " + createdToken.getOtp() + "");
            return new ResponseEntity<>(new ApiResponse("OTP is sent to your email", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("User not exist with this email", request.getEmail()), HttpStatus.OK);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        PasswordResetToken token = this.userService.getByOtp(request.getOtp());
        if (token == null) {
            return new ResponseEntity<>(new ApiResponse("OTP is Invalid", null), HttpStatus.NOT_FOUND);
        } else if (token.getExpiryDate().getTime() - System.currentTimeMillis() <= 0) {
            return new ResponseEntity<>(new ApiResponse("OTP is Expired", null), HttpStatus.OK);
        } else {
            User user = token.getUser();
            user.setPassword(encoder.encode(request.getPassword()));
            User updatedUser = this.userService.createUser(user);
            this.emailService.sendEmail(updatedUser.getEmail(), "Password change request", "Dear " + updatedUser.getFirstName() + ",\n\nYou have successfully changed your password.");
            return new ResponseEntity<>(new ApiResponse("Password reset successfully", updatedUser), HttpStatus.OK);
        }
    }

}
