package com.example.jwt.employee.employee.repository;

import com.example.jwt.employee.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
