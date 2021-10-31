package com.mastery.java.task.rest;

import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.service.EmployeeNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest extends AbstractEmployeeControllerTest {

    protected Employee createdEmployee =
            new Employee(1, "James", "Barrie", 0, "writer", Gender.MALE, LocalDate.of(1860, 5, 9));

    private EmployeeService EmployeeService;

	private EmployeeController employeeController;

	private MockMvc unitMockMvc;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeAll
    @Override
    protected void set() {
		existentId = 10;
        EmployeeService = mock(EmployeeService.class);
        employeeController = new EmployeeController(EmployeeService, modelMapper);
        Mockito.when(EmployeeService.getAllEmployees())
			   .thenReturn(Arrays.asList(employee));
        Mockito.when(EmployeeService.getEmployee(existentId))
               .thenReturn(employee);
        Mockito.when(EmployeeService.getEmployee(nonExistentId)).thenThrow(EmployeeNotFoundException.class);
        Mockito.when(EmployeeService.createEmployee(ArgumentMatchers.any(Employee.class))).thenReturn(createdEmployee);
        Mockito.doThrow(EmployeeNotFoundException.class).when(EmployeeService)
               .updateEmployee(Mockito.eq(nonExistentId), ArgumentMatchers.any(Employee.class));
        Mockito.doThrow(EmployeeNotFoundException.class).when(EmployeeService)
			   .deleteEmployee(nonExistentId);
		Mockito.doNothing().when(EmployeeService)
               .deleteEmployee(existentId);
		unitMockMvc = MockMvcBuilders.standaloneSetup(employeeController)
									 .build();
		setMockmvc(unitMockMvc);
	}

    public void whenGetNonExistentEmployee_thenStatusNotFound() throws Exception {
        assertThrows(NestedServletException.class, () -> unitMockMvc.perform(get("/api/employees/" + nonExistentId)));
    }

    public void whenUpdateNonExistentEmployees_thenStatusNotFound() throws Exception {
        assertThrows(NestedServletException.class, () -> unitMockMvc
                .perform(put("/api/employees/" + nonExistentId).content(asJsonString(employeeDto))));
    }

    public void whenDeleteNonExistentEmployees_thenStatusNotFound() throws Exception {
        assertThrows(NestedServletException.class,
                     () -> unitMockMvc.perform(delete("/api/employees/" + nonExistentId)));
    }
}
