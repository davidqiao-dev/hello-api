package com.qiao.hello;

import com.qiao.hello.entity.Department;
import com.qiao.hello.entity.Employee;
import com.qiao.hello.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldSaveAndFindByDepartment() {
        Employee employee = new Employee();
        employee.setName("Test User");
        employee.setSalary(new BigDecimal("9000.00"));
        employee.setHireDate(LocalDate.of(2024, 1, 1));
        employeeRepository.save(employee);

        var foundEmployee = employeeRepository.findByDepartment_Name("Testing");

        assertThat(foundEmployee).hasSize(1);
        assertThat(foundEmployee.get(0).getName()).isEqualTo("Test User");

    }

    @Test
    void shouldFindEmployeeByDepartmentName() {
        Department department = new Department();
        department.setName("Testing");
        department.setLocation("Nowhere");
        entityManager.persist(department);

        Employee employee = new Employee();
        employee.setName("Test User");
        employee.setSalary(new BigDecimal("9000.00"));
        employee.setHireDate(LocalDate.of(2024, 1, 1));
        employee.setDepartment(department);
        entityManager.persist(employee);

        entityManager.flush();
        var foundEmployee = employeeRepository.findByDepartment_Name(department.getName());
        assertThat(foundEmployee).hasSize(1);
    }

    @Test
    void shouldReturnFirstPageOfSizeOne() {
        Department department = new Department();
        department.setName("Testing");

        shouldFindEmployeeByDepartmentName();
        var firstPage = employeeRepository.findByDepartment_Name(department.getName(),
                PageRequest.of(0, 1));
        assertThat(firstPage.getContent()).hasSize(1);
        assertThat(firstPage.getSize()).isEqualTo(1);
    }
}
