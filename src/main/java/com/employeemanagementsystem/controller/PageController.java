package com.employeemanagementsystem.controller;

import com.employeemanagementsystem.entity.Employee;
import com.employeemanagementsystem.service.EmployeeJspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private EmployeeJspService employeeService;

    @GetMapping("login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/employee/list";
    }

    @GetMapping("employee/list")
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/employee/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        return "employee-view";
    }

    @GetMapping("/employee/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        return "employee-update";
    }

    @PostMapping("/employee/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                @ModelAttribute Employee employee) {
        if (employee == null) {
            return "redirect:/employee/list";
        }
        employeeService.updateEmployee(id, employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-add";
    }

    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        if (employee == null) {
            return "redirect:/employee/list";
        }
        employeeService.createEmployeeFromForm(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee/list";
    }
}