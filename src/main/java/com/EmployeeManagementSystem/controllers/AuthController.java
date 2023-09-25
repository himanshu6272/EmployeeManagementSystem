package com.EmployeeManagementSystem.controllers;

import com.EmployeeManagementSystem.exceptions.ApiResponse;
import com.EmployeeManagementSystem.models.AuthRequest;
import com.EmployeeManagementSystem.services.CustomUserDetailsService;
import com.EmployeeManagementSystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request){
        this.manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token =this.jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new ApiResponse("LoggedIn Successfully", token), HttpStatus.OK);
    }
}
