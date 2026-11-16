package com.qiao.hello.service;

import com.qiao.hello.dto.EmployeeRequestDto;
import com.qiao.hello.dto.EmployeeResponseDto;
import com.qiao.hello.entity.Department;
import com.qiao.hello.entity.Employee;
import com.qiao.hello.exception.DepartmentNotFoundException;
import com.qiao.hello.exception.EmployeeNotFoundException;
import com.qiao.hello.repository.DepartmentRepository;
import com.qiao.hello.repository.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findAll() {

        return employeeRepository.findAll().stream().map(EmployeeResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findByDepartment(String dept) {
        return employeeRepository.findByDepartment_Name(dept)
                .stream().map(EmployeeResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto findById(Long id) {
        return EmployeeResponseDto.from(getEmployeeById(id));
    }

    private Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(id)
                );
    }

    @Transactional
    public EmployeeResponseDto create(EmployeeRequestDto employeeReqDto) {
        Department department = departmentRepository
                .findById(employeeReqDto.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(employeeReqDto.departmentId()));
        Employee employee = new Employee();
        employee.setName(employeeReqDto.name());
        employee.setEmail(employeeReqDto.email());
        employee.setSalary(employeeReqDto.salary());
        employee.setHireDate(employeeReqDto.hireDate());
        employee.setDepartment(department);
        return EmployeeResponseDto.from(employeeRepository.save(employee));
    }

    public EmployeeResponseDto update(Long id, EmployeeRequestDto employeeRequestDto) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setName(employeeRequestDto.name());
        existingEmployee.setEmail(employeeRequestDto.email());
        existingEmployee.setSalary(employeeRequestDto.salary());
        existingEmployee.setHireDate(employeeRequestDto.hireDate());
        Department department = departmentRepository
                .findById(employeeRequestDto.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(employeeRequestDto.departmentId()));
        existingEmployee.setDepartment(department);
        return EmployeeResponseDto.from(employeeRepository.save(existingEmployee));
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    public EmployeeResponseDto assignDepartment(Long employeeId, Long departmentId) {
        Employee employee = getEmployeeById(employeeId);
        Department department = getDepartmentById(departmentId);
        employee.setDepartment(department);
        return EmployeeResponseDto.from(employeeRepository.save(employee));
    }
}
