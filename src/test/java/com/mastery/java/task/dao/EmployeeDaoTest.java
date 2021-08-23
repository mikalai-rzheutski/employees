package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeDaoTest {
	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private EmployeeDao employeeDao;

	private Employee employee;

	@BeforeAll
	public void setUp() {
		employee = new Employee("Peter", "Pen", 1, "character", Gender.MALE, LocalDate.of(1902, 1, 1));
	}

	@Test
	public void returnEmployeeIfExistsOrNullIfNotExists() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.eq("SELECT * FROM employee WHERE id = ?"), ArgumentMatchers.any(EmployeeRowMapper.class), Mockito.eq(1)))
			   .thenReturn(employee);
		Mockito.when(jdbcTemplate.queryForObject(Mockito.eq("SELECT * FROM employee WHERE id = ?"), ArgumentMatchers.any(EmployeeRowMapper.class), Mockito.eq(10)))
			   .thenThrow(EmptyResultDataAccessException.class);
		assertEquals(employee, employeeDao.getEmployeeById(1));
		assertNull(employeeDao.getEmployeeById(10));
	}

	@Test
	public void returnIdOfCreatedEmployee() {
		Mockito.when(jdbcTemplate.update(ArgumentMatchers.any(PreparedStatementCreator.class), ArgumentMatchers.any(KeyHolder.class)))
			   .then(invocation -> {
				   invocation
						   .<KeyHolder>getArgument(1).getKeyList()
													 .add(Collections.singletonMap("", 2));
				   return 1;
			   });
		assertEquals(2, employeeDao.addEmployee(employee));
	}

}
