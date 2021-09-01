package com.mastery.java.task.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractEmployeeControllerTest {

	protected Employee employee = new Employee("Peter", "Pen", 1, "worker", Gender.MALE, LocalDate.of(1902, 1, 1));

	protected int existentId;

	protected int nonExistentId = Integer.MAX_VALUE;

	private MockMvc mockMvc;

	private static String asJsonString(final Object obj) {
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
		mockMvc.perform(post("/api/employees").content(asJsonString(employee))
											  .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isCreated());
	}

	@Test
	@Transactional
	public void whenGetAllEmployees_thenStatusOk() throws Exception {
		mockMvc.perform(get("/api/employees"))
			   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void whenGetExistentEmployees_thenStatusOk() throws Exception {
		mockMvc.perform(get("/api/employees/" + existentId))
			   .andExpect(status().isOk())
			   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	@Transactional
	public void whenGetNonExistentEmployees_thenStatusNotFound() throws Exception {
		mockMvc.perform(get("/api/employees/" + nonExistentId))
			   .andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void whenUpdateExistentEmployees_thenStatusOk() throws Exception {
		mockMvc.perform(put("/api/employees/" + existentId).content(asJsonString(employee))
														   .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void whenUpdateNonExistentEmployees_thenStatusNotFound() throws Exception {
		mockMvc.perform(put("/api/employees/" + nonExistentId).content(asJsonString(employee))
															  .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void whenDeleteExistentEmployees_thenStatusOk() throws Exception {
		mockMvc.perform(delete("/api/employees/" + existentId))
			   .andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void whenDeleteNonExistentEmployees_thenStatusNotFound() throws Exception {
		mockMvc.perform(delete("/api/employees/" + nonExistentId))
			   .andExpect(status().isNotFound());
	}

}
