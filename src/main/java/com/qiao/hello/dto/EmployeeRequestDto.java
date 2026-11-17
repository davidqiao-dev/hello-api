package com.qiao.hello.dto;

import com.qiao.hello.entity.Employee;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequestDto(

        @NotBlank(message = "name must not be blank")
        String name,

        @Email(message = "invalid email format")
        String email,

        @NotNull(message = "salary is required")
        @Positive(message = "salary must be positive")
        BigDecimal salary,

        @NotNull(message = "hire date is required")
        @PastOrPresent(message = "hire date cannot be in the future")
        LocalDate hireDate,
        Long departmentId
) {
        public static Employee toEntity(EmployeeRequestDto employeeRequestDto) {
                Employee employee = new Employee();
                employee.setName(employeeRequestDto.name());
                employee.setEmail(employeeRequestDto.email());
                employee.setSalary(employeeRequestDto.salary());
                employee.setHireDate(employeeRequestDto.hireDate());
                return employee;
        }

        public static void setEntity(EmployeeRequestDto employeeRequestDto,
                                        Employee employee) {
                employee.setName(employeeRequestDto.name());
                employee.setEmail(employeeRequestDto.email());
                employee.setSalary(employeeRequestDto.salary());
                employee.setHireDate(employeeRequestDto.hireDate());
        }
}
