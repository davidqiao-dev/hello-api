package com.qiao.hello.repository;

import com.qiao.hello.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByDepartment(String department);
    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, BigDecimal salary);
    List<Employee> findBySalaryBetween(BigDecimal min, BigDecimal max);
    List<Employee> findByNameContaining(String keyword);

    @Query("select e from employee e where e.department = :dept order by e.salary desc")
    List<Employee> employeesInDepartmentOrderedBySalary(@Param("dept") String dept);
}
