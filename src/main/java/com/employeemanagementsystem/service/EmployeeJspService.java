package com.employeemanagementsystem.service;

import com.employeemanagementsystem.entity.Employee;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeJspService {

    private final PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder defaultPasswordEncoder;
    private List<Employee> employees = new ArrayList<>();

    public EmployeeJspService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.defaultPasswordEncoder = new BCryptPasswordEncoder();
    }

    public void createEmployeeFromForm(Employee employee) {
        if (employee.getName() == null || employee.getEmail() == null) {
            throw new IllegalArgumentException("Name and email are required");
        }

        employee.setId((long) (employees.size() + 1));
        employee.setPassword(passwordEncoder.encode("default123"));
        employee.setRole("ROLE_USER");
        employee.setDepartment("New Employee");
        employee.setSalary(0.0);
        employees.add(employee);
    }

    public void updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setRole(employee.getRole());
    }
    public List<Employee> getAllEmployees() {
        return employees;
    }
    public Employee getEmployeeById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public void deleteEmployee(Long id) {
        Employee employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employees.remove(employee);
    }
}
