package com.qiao.hello;

import com.qiao.hello.entity.Employee;
import com.qiao.hello.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldSaveAndFindByDepartment() {
        Employee employee = new Employee();
        employee.setName("Test User");
        employee.setDepartment("Testing");
        employee.setSalary(new BigDecimal("9000.00"));
        employee.setHireDate(LocalDate.of(2024, 1, 1));
        employeeRepository.save(employee);

        var foundEmployee = employeeRepository.findByDepartment("Testing");

        assertThat(foundEmployee).hasSize(1);
        assertThat(foundEmployee.get(0).getName()).isEqualTo("Test User");

    }
}
