package com.EmployeeManagementSystem.dao;

import com.EmployeeManagementSystem.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.email = :email")
    public boolean employeeExist(@Param("email") String email);
}
