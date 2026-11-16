package com.qiao.hello.dto;

import com.qiao.hello.entity.Department;
import com.qiao.hello.entity.Employee;

import java.math.BigDecimal;

public record EmployeeResponseDto(
        Long id,
        String name,
        String email,
        BigDecimal salary,
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
                department == null ? null : department.getId(),
                department == null ? null : department.getName()
        );
    }
}
