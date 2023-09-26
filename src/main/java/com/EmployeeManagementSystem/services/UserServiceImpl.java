package com.EmployeeManagementSystem.services;

import com.EmployeeManagementSystem.dao.PasswordResetTokenRepository;
import com.EmployeeManagementSystem.dao.UserRepository;
import com.EmployeeManagementSystem.exceptions.ResourceNotFoundException;
import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import com.EmployeeManagementSystem.models.Role;
import com.EmployeeManagementSystem.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Override
    public User createUser(User user) {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        if (user.getId() == 0) {
            role.setName("ROLE_USER");
            role.setUser(user);
            roles.add(role);
            user.setRoles(roles);
            log.info(user);
        } else {
            User oldUser = this.userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "Id", user.getId()));
            if (user.getRoles() == null) {
                user.setRoles(oldUser.getRoles());
            } else {
                List<Role> roleList = user.getRoles();
                for (Role role1 : roleList) {
                    role1.setUser(user);
                }
                user.setRoles(roleList);
            }
            user.setPassword(oldUser.getPassword());
        }
        return this.userRepository.save(user);
    }

    @Override
    public boolean userExist(String email) {
        return this.userRepository.userExist(email);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
    }

    @Override
    public void deleteUserById(int id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        this.userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public PasswordResetToken saveToken(PasswordResetToken token) {
        return this.tokenRepository.save(token);
    }

    @Override
    public PasswordResetToken getByOtp(String otp) {
        return this.tokenRepository.findByOtp(otp);
    }

    @Override
    public void deleteToken(int id) {
        this.tokenRepository.deleteByUserId(id);
    }
}
