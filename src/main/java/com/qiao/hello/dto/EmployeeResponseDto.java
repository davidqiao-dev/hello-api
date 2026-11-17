package com.qiao.hello.dto;

import com.qiao.hello.entity.Department;
import com.qiao.hello.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponseDto(
        Long id,
        String name,
        String email,
        BigDecimal salary,
        LocalDate hireDate,
        Long departmentId,
        String departmentName
) {
    public static EmployeeResponseDto from(Employee employee) {
        Department department = employee.getDepartment();
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getHireDate(),
                department == null ? null : department.getId(),
                department == null ? null : department.getName()
        );
    }
}
