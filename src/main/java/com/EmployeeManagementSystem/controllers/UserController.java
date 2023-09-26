package com.EmployeeManagementSystem.controllers;

import com.EmployeeManagementSystem.exceptions.ApiResponse;
import com.EmployeeManagementSystem.models.Role;
import com.EmployeeManagementSystem.models.User;
import com.EmployeeManagementSystem.services.EmailService;
import com.EmployeeManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user){
        if (this.userService.userExist(user.getEmail())){
            return new ResponseEntity<>(new ApiResponse("User Already exists with this email", user.getEmail()), HttpStatus.CONFLICT);
        }else {
            User createdUser = this.userService.createUser(user);
            if (createdUser != null){
                this.emailService.sendEmail(createdUser.getEmail(), "Welcome to Inexture!", "Dear " + createdUser.getFirstName() + ",\n\nWelcome to our Organization! We are excited to have you as a member and your account is successfully registered in our Management Portal.");
            }
            return new ResponseEntity<>(new ApiResponse("User Created Successfully", createdUser), HttpStatus.CREATED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user, Principal principal){
        String email = principal.getName();
        User loggedUser = this.userService.getUserByEmail(email);
        List<Role> roles = loggedUser.getRoles();
        if(roles.contains("ROLE_ADMIN")){
            User updatedUser = this.userService.createUser(user);
            if (updatedUser != null){
                this.emailService.sendEmail(updatedUser.getEmail(), "Welcome to Inexture!", "Dear " + updatedUser.getFirstName() + ",\n\nYour personal details is successfully updated.");
            }
            return new ResponseEntity<>(new ApiResponse("User Updated Successfully", updatedUser), HttpStatus.OK);
        }else {
            if (user.getId() == loggedUser.getId()){
                user.setRoles(loggedUser.getRoles());
                User updatedUser = this.userService.createUser(user);
                if (updatedUser != null){
                    this.emailService.sendEmail(updatedUser.getEmail(), "Welcome to Inexture!", "Dear " + updatedUser.getFirstName() + ",\n\nYour personal details is successfully updated.");
                }
                return new ResponseEntity<>(new ApiResponse("User Updated Successfully", updatedUser), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ApiResponse("You can only update your own Profile", null), HttpStatus.OK);
            }

        }

    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") int id, Principal principal){
        String email = principal.getName();
        User loggedUser = this.userService.getUserByEmail(email);
        List<Role> roles = loggedUser.getRoles();
        if (roles.contains("ROLE_ADMIN")) {
            User user = this.userService.getUserById(id);
            return new ResponseEntity<>(new ApiResponse("Get User by Id", user), HttpStatus.FOUND);
        }else {
            if (id == loggedUser.getId()){
                User user = this.userService.getUserById(id);
                return new ResponseEntity<>(new ApiResponse("Get User by Id", user), HttpStatus.FOUND);
            }else {
                return new ResponseEntity<>(new ApiResponse("You can only view your own profile", null), HttpStatus.FOUND);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int id){
        this.userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(new ApiResponse("All user List", users), HttpStatus.OK);
    }
}
