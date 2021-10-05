package com.mastery.java.task.service;

import com.mastery.java.task.dal.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

public class JpaEmployeeServiceTest extends AbstractEmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Override
    @BeforeAll
    public void initMock() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(employeeRepository.findById(0)).thenReturn(Optional.ofNullable(employee));
        Mockito.doThrow(NoSuchElementException.class)
                .when(employeeRepository)
                .findById(1);
        Mockito.when(employeeRepository.existsById(0)).thenReturn(true);
        employeeService = new JpaEmployeeService(employeeRepository);
    }

}
