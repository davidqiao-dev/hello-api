package com.qiao.hello.service;

import com.qiao.hello.entity.Employee;
import com.qiao.hello.exception.EmployeeNotFoundException;
import com.qiao.hello.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {return employeeRepository.findAll();}

    public List<Employee> findByDepartment(String dept) {
        return employeeRepository.findByDepartment(dept);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                ()->new EmployeeNotFoundException(id)
        );
    }

    public Employee create(Employee employee) {
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existingEmployee = findById(id);

        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setHireDate(employee.getHireDate());
        return employeeRepository.save(existingEmployee);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
