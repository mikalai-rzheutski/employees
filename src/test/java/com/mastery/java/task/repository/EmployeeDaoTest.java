package com.mastery.java.task.repository;

import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeDaoTest {

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@InjectMocks
	private EmployeeDao employeeDao;

    private Employee employee, employeeToSave;

	@BeforeAll
    public void set() {
        employee = new Employee(1, "Peter", "Pen", 1, "character", Gender.MALE, LocalDate.of(1902, 1, 1));
        employeeToSave = new Employee(null, "Peter",
				"Pen",
				1,
				"character",
				Gender.MALE,
				LocalDate.of(1902, 1, 1));
		MockitoAnnotations.openMocks(this);

		Mockito.when(namedParameterJdbcTemplate.queryForObject(Mockito.eq(EmployeeDao.GET_EMPLOYEE_BY_ID),
				argThat(new IsSameId(new MapSqlParameterSource().addValue("id", 1))),
				ArgumentMatchers.any(EmployeeDao.EmployeeRowMapper.class)))
			   .thenReturn(employee);

        Mockito.when(namedParameterJdbcTemplate.queryForObject(Mockito.eq(EmployeeDao.UPDATE_EMPLOYEE), ArgumentMatchers
                                                                       .any(BeanPropertySqlParameterSource.class),
                                                               ArgumentMatchers.any(EmployeeDao.EmployeeRowMapper.class)))
               .thenReturn(employee);

		Mockito.when(namedParameterJdbcTemplate.update(Mockito.eq(EmployeeDao.INSERT_EMPLOYEE),
				ArgumentMatchers.any(BeanPropertySqlParameterSource.class),
				ArgumentMatchers.any(KeyHolder.class),
				Mockito.any(String[].class)))
				.then(invocation -> {
					invocation
							.<KeyHolder>getArgument(2)
                            .getKeyList().add(Collections.singletonMap("", 1));
					return 1;
				});
	}

	@Test
	public void returnEmployeeIfExistsOrNullIfNotExists() {
        assertEquals(employee, employeeDao.findById(1).get());
        assertFalse(employeeDao.findById(2).isPresent());
	}

	@Test
	public void returnIdOfCreatedEmployee() {
        assertEquals(employee, employeeDao.save(employee));
        assertEquals(employee, employeeDao.save(employeeToSave));
	}

	private static class IsSameId implements ArgumentMatcher<MapSqlParameterSource> {
		private final MapSqlParameterSource mapSqlParameterSource;

		public IsSameId(MapSqlParameterSource sqlParameterSource) {
			this.mapSqlParameterSource = sqlParameterSource;
		}

		@Override
		public boolean matches(MapSqlParameterSource mapSqlParameterSource) {
			return this.mapSqlParameterSource.getValue("id") ==
					mapSqlParameterSource.getValue("id");
		}
	}


}
