package com.qiao.hello.controller;

import com.qiao.hello.entity.Employee;
import com.qiao.hello.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.OptionalLong;

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

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("employee " + id + " not found"));
        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setHireDate(employee.getHireDate());
        return employeeRepository.save(existingEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}
