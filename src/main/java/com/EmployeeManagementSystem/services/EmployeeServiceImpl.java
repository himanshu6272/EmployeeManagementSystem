package com.EmployeeManagementSystem.services;

import com.EmployeeManagementSystem.dao.EmployeeRepository;
import com.EmployeeManagementSystem.exceptions.ResourceNotFoundException;
import com.EmployeeManagementSystem.models.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger log = Logger.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public boolean employeeExist(String email) {
        return this.employeeRepository.employeeExist(email);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getId() == 0) {
            log.info("New Employee");
        }else {
            Employee oldEmployee = this.employeeRepository.findById(employee.getId()).orElseThrow(()-> new ResourceNotFoundException("Employee", "Id", employee.getId()));
            employee.setId(oldEmployee.getId());
        }
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return this.employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        this.employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }
}
