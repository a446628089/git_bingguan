package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Employee;
import com.example.hotelmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee dbEmployee = employeeService.findById(id).orElseThrow(() -> new RuntimeException("员工不存在"));
        dbEmployee.setName(employee.getName() != null ? employee.getName() : dbEmployee.getName());
        dbEmployee.setPhone(employee.getPhone() != null ? employee.getPhone() : dbEmployee.getPhone());
        dbEmployee.setRole(employee.getRole() != null ? employee.getRole() : dbEmployee.getRole());
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            dbEmployee.setPassword(employee.getPassword());
        }
        return employeeService.save(dbEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @GetMapping(params = "username")
    public Employee getEmployeeByUsername(@RequestParam String username) {
        return employeeService.findByUsername(username);
    }
} 