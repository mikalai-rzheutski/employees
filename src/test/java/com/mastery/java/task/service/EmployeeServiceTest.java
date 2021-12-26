package com.mastery.java.task.service;

import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.repository.EmployeeJpaRepository;
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

  @Mock private EmployeeJpaRepository employeeJpaRepository;

  private EmployeeService employeeService;

  @BeforeAll
  public void set() {
    MockitoAnnotations.openMocks(this);
    Mockito.when(employeeJpaRepository.findById(0)).thenReturn(Optional.ofNullable(employee));
    Mockito.when(employeeJpaRepository.findById(1)).thenReturn(Optional.ofNullable(null));
    Mockito.when(employeeJpaRepository.existsById(0)).thenReturn(true);
    employeeService = new EmployeeService(employeeJpaRepository);
  }

  @Test
  public void returnEmployeeIfExistsOrThrowsExceptionIfNotExists() {
    assertEquals(employee, employeeService.getEmployee(0));
    assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployee(1));
  }

  @Test
  public void updateEmployeeIfExistsOrThrowsExceptionIfNotExists() {
    employee.setId(0);
    Assertions.assertDoesNotThrow(() -> employeeService.updateEmployee(employee));
    employee.setId(1);
    assertThrows(EntityNotFoundException.class, () -> employeeService.updateEmployee(employee));
  }

  @Test
  public void deleteEmployeeIfExistsOrThrowsExceptionIfNotExists() {
    Assertions.assertDoesNotThrow(() -> employeeService.deleteEmployee(0));
    assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(1));
  }
}
