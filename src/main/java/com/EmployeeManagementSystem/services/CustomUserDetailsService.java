package com.EmployeeManagementSystem.services;

import com.EmployeeManagementSystem.models.CustomUserDetails;
import com.EmployeeManagementSystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUserByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("User not found with this email!!");
        }else {
            return new CustomUserDetails(user);
        }
    }
}
