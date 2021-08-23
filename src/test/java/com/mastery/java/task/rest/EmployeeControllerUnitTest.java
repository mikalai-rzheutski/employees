package com.mastery.java.task.rest;

import com.mastery.java.task.rest.exceptions.NotFoundException;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest extends AbstarctEmployeeControllerTest {

	private EmployeeService employeeService;

	private EmployeeController employeeController;

	private MockMvc unitMockMvc;

	@BeforeAll
	public void setUp() {
		existentId = 10;
		employeeService = mock(EmployeeService.class);
		employeeController = new EmployeeController(employeeService);
		Mockito.when(employeeService.fetchAllEmployees())
			   .thenReturn(Arrays.asList(employee));
		Mockito.when(employeeService.getEmployee(existentId))
			   .thenReturn(employee);
		Mockito.when(employeeService.getEmployee(nonExistentId))
			   .thenThrow(NotFoundException.class);
		Mockito.when(employeeService.createEmployee(employee))
			   .thenReturn(existentId);
		Mockito.doThrow(NotFoundException.class)
			   .when(employeeService)
			   .updateEmployee(nonExistentId, employee);
		Mockito.doThrow(NotFoundException.class)
			   .when(employeeService)
			   .deleteEmployee(nonExistentId);
		Mockito.doNothing()
			   .when(employeeService)
			   .deleteEmployee(existentId);
		unitMockMvc = MockMvcBuilders.standaloneSetup(employeeController)
									 .build();
		setMockmvc(unitMockMvc);
	}
}
