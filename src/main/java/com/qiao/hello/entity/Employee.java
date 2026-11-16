package com.qiao.hello.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Setter
    @Getter
    @NotBlank(message = "name must not be blank")
    private String name;


    @Getter
    @Setter
    @Email(message = "invalid email format")
    private String email;

    @Getter
    @Setter
    @NotNull(message = "salary is required")
    @Positive(message = "salary must be positive")
    private BigDecimal salary;

    @Getter
    @Setter
    @NotNull(message = "hire date is required")
    @PastOrPresent(message = "hire date cannot be in the future")
    private LocalDate hireDate;

    //no-arg constructor required by JPA
    public Employee(){}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public void setDepartment(Department department){
        this.department = department;
    }
    public Department getDepartment(){return department;}
}
