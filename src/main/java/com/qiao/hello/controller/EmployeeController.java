package com.qiao.hello.controller;

import com.qiao.hello.dto.EmployeeRequestDto;
import com.qiao.hello.dto.EmployeeResponseDto;
import com.qiao.hello.entity.Employee;
import com.qiao.hello.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/by-department")
    public Page<EmployeeResponseDto> findByDepartment(
            @RequestParam String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size,Sort.by(dir,sort));
        return employeeService.findByDepartment(department, pageable);
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
