package com.qiao.hello.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String department;
    private BigDecimal salary;
    private LocalDate hireDate;

    //no-arg constructor required by JPA
    public Employee(){}

    //getters and setters for all fields
    public long getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public BigDecimal getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    //setters...


}
