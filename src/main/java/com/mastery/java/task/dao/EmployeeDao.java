package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDao {

	public static final String GET_EMPLOYEES = "SELECT * FROM employee ORDER BY id ASC";
	static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id = :id";
	static final String INSERT_EMPLOYEE = "INSERT INTO employee(" +
			"first_name, last_name, department_id, job_title, gender, date_of_birth)" +
			" VALUES (:firstName, :lastName, :departmentId, :jobTitle, CAST(:gender AS gender_type), :dateOfBirth)";
	static final String UPDATE_EMPLOYEE = "UPDATE employee SET first_name = :firstName," +
			" last_name = :lastName," +
			" department_id = :departmentId," +
			" job_title = :jobTitle," +
			" gender = CAST(:gender AS gender_type)," +
			" date_of_birth = :dateOfBirth" +
			" WHERE id = :id";
	static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM employee WHERE id = :id";
	static final String COUNT_EMPLOYEES_BY_ID = "SELECT COUNT(1) FROM employee WHERE id = :id";


	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * Returns a collection of all the records from the Employee table
	 *
	 * @return a collection of Employees mapped to the obtained record
	 */
	public List<Employee> getAllEmployees() {
		return namedParameterJdbcTemplate.query(GET_EMPLOYEES, new EmployeeRowMapper());
	}

	/**
	 * Returns a record from the Employee table by its identifier
	 * @param id an identifier of the record to be returned
	 * @return the Employee object mapped to the obtained record
	 */
	public Employee getEmployeeById(int id) {

		Employee employee;
		try {
			employee = namedParameterJdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID, new MapSqlParameterSource()
					.addValue("id", id), new EmployeeRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return employee;
	}

	/**
	 * Adds a new record to the Employee table
	 * @param employee an <code>Employee</code> object to be added
	 * @return an id of the new added record
	 */
	public int addEmployee(Employee employee) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT_EMPLOYEE, new EmployeePropertySqlParameterSource(employee), keyHolder, new String[]{"id"});
		return keyHolder.getKey().intValue();
	}

	/**
	 * Modifies an existent record in the Employee table
	 * @param id       an identifier of the record to be modified
	 * @param employee a <code>Employee</code> object containing new values
	 * @return a number of the modified records. Normally, it should be equal to one.
	 */
	public int updateEmployee(int id, Employee employee) {
		employee.setId(id);
		return namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE, new EmployeePropertySqlParameterSource(employee));
	}

	/**
	 * Deletes a record in the Employee table
	 * @param id an identifier of the record to be deleted
	 */
	public void deleteEmployee(int id) {
		namedParameterJdbcTemplate.update(DELETE_EMPLOYEE_BY_ID, new MapSqlParameterSource()
				.addValue("id", id));
	}

	/**
	 * Checks if the record with given identifier exists in the database
	 *
	 * @param id an identifier of the record to be checked
	 * @return true if the record with given identifier exists in the database or false if it does not
	 */
	public boolean isEmployeeExistent(int id) {
		return namedParameterJdbcTemplate.queryForObject(COUNT_EMPLOYEES_BY_ID, new MapSqlParameterSource()
				.addValue("id", id), Integer.class) > 0;
	}

	public static class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
			Employee employee = new Employee(rs.getObject(1, Integer.class),
					rs.getString(2),
					rs.getString(3),
					rs.getObject(4, Integer.class),
					rs.getString(5),
					Gender.valueOf(rs.getString(6)),
					rs.getDate(7)
							.toLocalDate());
			return employee;
		}
	}

	private static class EmployeePropertySqlParameterSource extends BeanPropertySqlParameterSource {
		public EmployeePropertySqlParameterSource(Object object) {
			super(object);
		}

		@Override
		public Object getValue(String paramName) throws IllegalArgumentException {
			Object value = super.getValue(paramName);
			if (value instanceof Enum) {
				return value.toString();
			}
			if (value instanceof LocalDate) {
				return Date.valueOf((LocalDate) value);
			}
			return value;
		}
	}
}