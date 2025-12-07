package com.example.jwt.employee.employee.Controller;

import com.example.jwt.employee.employee.Model.EmployeeRequest;
import com.example.jwt.employee.employee.entity.Employee;
import com.example.jwt.employee.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id){
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }
    @PostMapping("/employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        int id=employeeService.addEmployee(employeeRequest);
        return ResponseEntity.ok("Employee Save with id:"+id);
    }
    @PutMapping("/employee")
    public ResponseEntity<String> updateSalary(
            @RequestParam int id,@RequestParam int salary
    ){
        employeeService.updateSalary(id,salary);
        return ResponseEntity.ok("Employee is updated");
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee is deleted with id:"+id);
    }
}
