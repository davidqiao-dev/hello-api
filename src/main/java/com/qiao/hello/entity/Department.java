package com.qiao.hello.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String location;

    @OneToMany(mappedBy = "department") //inverse side - no FK column here
    private List<Employee> employees = new ArrayList<Employee>();

    public Department() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
}
