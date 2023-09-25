package com.EmployeeManagementSystem.services;

import com.EmployeeManagementSystem.models.Employee;

import java.util.List;

public interface EmployeeService {
    boolean employeeExist(String email);

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<Employee> getAllEmployees();
}
