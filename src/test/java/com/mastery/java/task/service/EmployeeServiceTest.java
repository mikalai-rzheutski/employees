package com.mastery.java.task.service;

import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.repository.EmployeeJpaRepository;
import com.mastery.java.task.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {
    protected Employee employee =
            new Employee(null, "Peter", "Pen", 1, "character", Gender.MALE, LocalDate.of(1902, 1, 1));

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeAll
    public void set() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(employeeRepository.findById(0)).thenReturn(Optional.ofNullable(employee));
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        Mockito.when(employeeRepository.existsById(0)).thenReturn(true);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void returnEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        assertEquals(employee, employeeService.getEmployee(0));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1));
    }

    @Test
    public void updateEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        Assertions.assertDoesNotThrow(() -> employeeService.updateEmployee(0, employee));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(1, employee));
    }

    @Test
    public void deleteEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        Assertions.assertDoesNotThrow(() -> employeeService.deleteEmployee(0));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1));
    }
}
