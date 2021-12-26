package com.mastery.java.task.rest;

import com.mastery.java.task.jms.JmsProducer;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.service.EmployeeService;
import com.mastery.java.task.service.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest extends AbstractEmployeeControllerTest {

  protected Employee createdEmployee =
      new Employee(1, "James", "Barrie", 0, "writer", Gender.MALE, LocalDate.of(1860, 5, 9));

  private EmployeeService employeeService;

  private JmsProducer jmsProducer;

  private EmployeeController employeeController;

  private MockMvc unitMockMvc;

  private ModelMapper modelMapper = new ModelMapper();

  @BeforeAll
  @Override
  protected void set() {
    existentId = 10;
    employeeService = mock(EmployeeService.class);
    jmsProducer = mock(JmsProducer.class);
    employeeController = new EmployeeController(employeeService, modelMapper, jmsProducer);
    Mockito.when(employeeService.getEmployees()).thenReturn(Arrays.asList(employee));
    Mockito.when(employeeService.getEmployee(existentId)).thenReturn(employee);
    Mockito.when(employeeService.getEmployee(nonExistentId))
        .thenThrow(EntityNotFoundException.class);
    Mockito.when(employeeService.createEmployee(ArgumentMatchers.any(Employee.class)))
        .thenReturn(createdEmployee);
    Mockito.doThrow(EntityNotFoundException.class)
        .when(jmsProducer)
        .updateEmployee(argThat(new EmployeeWithNonExistentId()));
    Mockito.doThrow(EntityNotFoundException.class)
        .when(employeeService)
        .deleteEmployee(nonExistentId);
    Mockito.doNothing().when(employeeService).deleteEmployee(existentId);
    unitMockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    setMockmvc(unitMockMvc);
  }

  public void whenGetNonExistentEmployee_thenStatusNotFound() throws Exception {
    assertThrows(
        NestedServletException.class,
        () -> unitMockMvc.perform(get("/api/employees/" + nonExistentId)));
  }

  public void whenUpdateNonExistentEmployees_thenStatusNotFound() throws Exception {
    assertThrows(
        NestedServletException.class,
        () ->
            unitMockMvc.perform(
                put("/api/employees/" + nonExistentId).content(asJsonString(employeeDto))));
  }

  public void whenDeleteNonExistentEmployees_thenStatusNotFound() throws Exception {
    assertThrows(
        NestedServletException.class,
        () -> unitMockMvc.perform(delete("/api/employees/" + nonExistentId)));
  }

  private static class EmployeeWithNonExistentId implements ArgumentMatcher<Employee> {

    @Override
    public boolean matches(Employee employee) {
      return employee.getId() == nonExistentId;
    }
  }
}
