package com.qiao.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/api/employees/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return new Employee(id, "Zhang Wei", "IT");
    }

    @GetMapping("/api/employees")
    public String listEmployees(@RequestParam(required = false) String department) {
        return "Department filter: " + department;
    }
}
