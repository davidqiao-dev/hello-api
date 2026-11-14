package com.qiao.hello.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String name;

    @Getter
    @Setter
    private String department;

    @Getter
    @Setter
    private BigDecimal salary;

    @Getter
    @Setter
    private LocalDate hireDate;

    //no-arg constructor required by JPA
    public Employee(){}


}
