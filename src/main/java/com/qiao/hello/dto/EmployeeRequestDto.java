package com.qiao.hello.dto;

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
}
