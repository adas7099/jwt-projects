package com.example.jwt.employee.employee.service;

import com.example.jwt.employee.employee.Model.EmployeeRequest;
import com.example.jwt.employee.employee.entity.Employee;
import com.example.jwt.employee.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployee(int id){
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
        if(employeeOptional.isEmpty())
            throw new NoSuchElementException("NO employee present for id: "+id);
        return employeeOptional.get();
    }
    public void updateSalary(int id,int sal){
        Employee employee=getEmployee(id);
        employee.setSalary(sal);
        employeeRepository.save(employee);
    }
    public int addEmployee(EmployeeRequest employeeRequest){
        Employee employee=Employee.builder()
                .ename(employeeRequest.getEname())
                .salary(employeeRequest.getSalary())
                .deptname(employeeRequest.getDeptname()).build();
        employee=employeeRepository.save(employee);
        return employee.getEid();
    }
    public void deleteEmployee(int id){
        Employee employee=getEmployee(id);
        employeeRepository.delete(employee);
    }
}
