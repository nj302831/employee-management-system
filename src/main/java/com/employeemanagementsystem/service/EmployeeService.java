package com.employeemanagementsystem.service;

import com.employeemanagementsystem.dto.EmployeeDto;
import com.employeemanagementsystem.entity.Employee;
import com.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void createEmployee(EmployeeDto dto) {
        Optional<Employee> existingEmployee = employeeRepository.findByEmailAndName(dto.getEmail(), dto.getName());

        if (existingEmployee.isPresent()) {
            throw new RuntimeException("Employee with this name and email already exists.");
        }

        String role = dto.getRole();

        if ("USER".equalsIgnoreCase(role)) {
            throw new RuntimeException("Only admin can create employee.");
        }
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            role = "ADMIN";
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        employee.setRole("ROLE_" + role.toUpperCase());

        employeeRepository.save(employee);
    }

    public void updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

}

