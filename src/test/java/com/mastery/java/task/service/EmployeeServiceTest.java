package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.rest.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {
	@Mock
	private EmployeeDao employeeDao;

	@InjectMocks
	private EmployeeService employeeService;

	private Employee employee;

	@BeforeAll
	public void setUp() {
		employee = new Employee("Peter", "Pen", 1, "character", Gender.MALE, LocalDate.of(1902, 1, 1));
	}

	@Test
	public void returnEmployeeIfExistsOrThrowsExceptionIfNotExists() {
		Mockito.when(employeeDao.getEmployeeById(0))
			   .thenReturn(employee);
		assertEquals(employee, employeeService.getEmployee(0));
		assertThrows(NotFoundException.class, () -> employeeService.getEmployee(1));
	}

	@Test
	public void updateEmployeeIfExistsOrThrowsExceptionIfNotExists() {
		Mockito.when(employeeDao.getEmployeeById(0))
			   .thenReturn(employee);
		Assertions.assertDoesNotThrow(() -> employeeService.updateEmployee(0, employee));
		assertThrows(NotFoundException.class, () -> employeeService.updateEmployee(1, employee));
	}

	@Test
	public void deleteEmployeeIfExistsOrThrowsExceptionIfNotExists() {
		Mockito.when(employeeDao.getEmployeeById(0))
			   .thenReturn(employee);
		Assertions.assertDoesNotThrow(() -> employeeService.deleteEmployee(0));
		assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(1));
	}

}
