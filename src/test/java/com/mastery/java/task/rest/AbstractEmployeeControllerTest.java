package com.mastery.java.task.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.dtos.employee.EmployeeDto;
import com.mastery.java.task.model.entities.employee.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractEmployeeControllerTest {

  protected static int nonExistentId = Integer.MAX_VALUE;

  protected EmployeeDto employeeDto =
      new EmployeeDto(null, "Peter", "Pen", 1, "worker", Gender.MALE, LocalDate.of(1902, 1, 1));

  protected Employee employee =
      new Employee(null, "James", "Barrie", 0, "writer", Gender.MALE, LocalDate.of(1860, 5, 9));

  protected int existentId;

  private MockMvc mockMvc;

  protected static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract void set();

  protected void setMockmvc(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Test
  @Transactional
  public void whenCreateEmployee_thenStatusCreated() throws Exception {
    mockMvc
        .perform(
            post("/api/employees")
                .content(asJsonString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @Transactional
  public void whenGetAllEmployees_thenStatusOk() throws Exception {
    mockMvc
        .perform(get("/api/employees"))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  public void whenGetExistentEmployees_thenStatusOk() throws Exception {
    mockMvc
        .perform(get("/api/employees/" + existentId))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  @Transactional
  public void whenGetNonExistentEmployee_thenStatusNotFound() throws Exception {
    mockMvc.perform(get("/api/employees/" + nonExistentId)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void whenUpdateExistentEmployees_thenStatusOk() throws Exception {
    mockMvc
        .perform(
            put("/api/employees/" + existentId)
                .content(asJsonString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  public void whenUpdateNonExistentEmployees_thenStatusNotFound() throws Exception {
    mockMvc
        .perform(
            put("/api/employees/" + nonExistentId)
                .content(asJsonString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void whenDeleteExistentEmployees_thenStatusOk() throws Exception {
    mockMvc.perform(delete("/api/employees/" + existentId)).andExpect(status().isOk());
  }

  @Test
  @Transactional
  public void whenDeleteNonExistentEmployees_thenStatusNotFound() throws Exception {
    mockMvc.perform(delete("/api/employees/" + nonExistentId)).andExpect(status().isNotFound());
  }
}
