package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.rest.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractEmployeeServiceTest {
    protected Employee employee = new Employee("Peter", "Pen", 1, "character", Gender.MALE, LocalDate.of(1902, 1, 1));

    protected EmployeeService employeeService;


    protected abstract void initMock();

    @Test
    public void returnEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        assertEquals(employee, employeeService.getEmployee(0));
        assertThrows(NotFoundException.class, () -> employeeService.getEmployee(1));
    }

    @Test
    public void updateEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        Assertions.assertDoesNotThrow(() -> employeeService.updateEmployee(0, employee));
        assertThrows(NotFoundException.class, () -> employeeService.updateEmployee(1, employee));
    }

    @Test
    public void deleteEmployeeIfExistsOrThrowsExceptionIfNotExists() {
        Assertions.assertDoesNotThrow(() -> employeeService.deleteEmployee(0));
        assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(1));
    }
}
