package com.mastery.java.task.rest;

import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
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
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EmployeeControllerIntegrationTest extends AbstractEmployeeControllerTest {

  @Autowired private EmployeeService EmployeeService;

  @Autowired private MockMvc mockMvc;

  @BeforeAll
  @Override
  protected void set() {
    existentId = EmployeeService.createEmployee(employee).getId();
    setMockmvc(mockMvc);
  }
}
