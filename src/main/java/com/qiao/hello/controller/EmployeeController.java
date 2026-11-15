package com.qiao.hello.controller;

import com.qiao.hello.entity.Employee;
import com.qiao.hello.repository.EmployeeRepository;
import com.qiao.hello.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> list(@RequestParam(required = false) String department) {
        if(department != null){
            return employeeService.findByDepartment(department);
        }
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Long id) {
        return  employeeService.findById(id);
    }

    @PostMapping
    public Employee create(@Valid @RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
