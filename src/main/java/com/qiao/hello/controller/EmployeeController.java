package com.qiao.hello.controller;

import com.qiao.hello.entity.Employee;
import com.qiao.hello.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository  employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> list(@RequestParam(required = false) String department) {
        if(department != null){
            return employeeRepository.findByDepartment(department);
        }
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Long id) {
        return  employeeRepository.findById(id).orElseThrow();
    }
}
