package com.mastery.java.task.rest;

import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EmployeeControllerIntegrationTest extends AbstarctEmployeeControllerTest {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private MockMvc mockMvc;


	@BeforeAll
	public void setUp() {
		existentId = employeeService.createEmployee(employee);
		setMockmvc(mockMvc);
	}
}
