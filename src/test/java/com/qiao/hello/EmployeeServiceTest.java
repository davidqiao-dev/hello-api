package com.qiao.hello;

import com.qiao.hello.exception.EmployeeNotFoundException;
import com.qiao.hello.repository.EmployeeRepository;
import com.qiao.hello.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void shouldThrowWhenEmployeeMissing() {

        assertThatThrownBy(() -> employeeService.findById(1L))
        .isInstanceOf(EmployeeNotFoundException.class);
    }
}
