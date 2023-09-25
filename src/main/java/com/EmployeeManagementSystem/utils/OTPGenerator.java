package com.EmployeeManagementSystem.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OTPGenerator {

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            otp.append(randomNumber);
        }

        return otp.toString();
    }
}
