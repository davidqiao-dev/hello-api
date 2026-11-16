package com.qiao.hello.controller;

import com.qiao.hello.dto.EmployeeRequestDto;
import com.qiao.hello.dto.EmployeeResponseDto;
import com.qiao.hello.entity.Employee;
import com.qiao.hello.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponseDto> list(@RequestParam(required = false) String department) {
        if (department != null) {
            return employeeService.findByDepartment(department);
        }
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDto getOne(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public EmployeeResponseDto create(@Valid @RequestBody EmployeeRequestDto employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDto update(@PathVariable Long id
            , @Valid @RequestBody EmployeeRequestDto employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/department/{deptId}")
    public EmployeeResponseDto assignDepartment(@PathVariable Long id
            , @PathVariable Long deptId) {
        return employeeService.assignDepartment(id, deptId);

    }

}
