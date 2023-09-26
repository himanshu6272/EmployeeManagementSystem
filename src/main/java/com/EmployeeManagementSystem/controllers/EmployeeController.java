package com.EmployeeManagementSystem.controllers;

import com.EmployeeManagementSystem.exceptions.ApiResponse;
import com.EmployeeManagementSystem.models.Employee;
import com.EmployeeManagementSystem.services.EmailService;
import com.EmployeeManagementSystem.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createEmployee(@RequestBody Employee employee, Principal principal){
        if (this.employeeService.employeeExist(employee.getEmail())){
            return new ResponseEntity<>(new ApiResponse("Employee Already exists with this email", employee.getEmail()), HttpStatus.CONFLICT);
        }else {
            Employee createdEmployee = this.employeeService.createEmployee(employee, principal);
            if (createdEmployee != null){
                this.emailService.sendEmail(createdEmployee.getEmail(), "Welcome to Inexture!", "Dear " + createdEmployee.getFirstName() + ",\n\nWelcome to our Organization! We are excited to have you as a member and your account is successfully registered in our Management Portal.");
            }
            return new ResponseEntity<>(new ApiResponse("Employee Created Successfully", createdEmployee), HttpStatus.CREATED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateEmployee(@RequestBody Employee employee, Principal principal){
        Employee updatedEmployee = this.employeeService.createEmployee(employee, principal);
        if (updatedEmployee != null){
            this.emailService.sendEmail(updatedEmployee.getEmail(), "Welcome to Inexture!", "Dear " + updatedEmployee.getFirstName() + ",\n\nYour personal details is successfully updated.");
        }
        return new ResponseEntity<>(new ApiResponse("Employee Updated Successfully", updatedEmployee), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable("id") int id){
        Employee employee = this.employeeService.getEmployeeById(id);
        return new ResponseEntity<>(new ApiResponse("Get Employee by Id", employee), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable("id") int id){
        this.employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(new ApiResponse("Employee deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllEmployees(){
        List<Employee> employees = this.employeeService.getAllEmployees();
        return new ResponseEntity<>(new ApiResponse("All Employee List", employees), HttpStatus.OK);
    }
}
