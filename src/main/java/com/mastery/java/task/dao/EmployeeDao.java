package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class EmployeeDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Employee> getAllEmployees() {
		String query = "SELECT * FROM employee ORDER BY id ASC";
		return jdbcTemplate.query(query, new EmployeeRowMapper());
	}

	public Employee getEmployeeById(int id) {
		String query = "SELECT * FROM employee WHERE id = ?";
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return employee;
	}

	/**
	 * Adds a new record to the Employee table
	 *
	 * @param employee a <code>Employee</code> object to be added
	 * @return an id of the new added record
	 */
	public int addEmployee(Employee employee) {
		String query = "INSERT INTO employee(first_name, last_name, department_id, job_title, gender, date_of_birth) VALUES (?, ?, ?, ?, CAST(? AS gender_type), ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, new String[]{"id"});
						ps.setString(1, employee.getFirstName());
						ps.setString(2, employee.getLastName());
						ps.setInt(3, employee.getDepartmentId());
						ps.setString(4, employee.getJobTitle());
						ps.setString(5, employee.getGender()
												.toString());
						ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
						return ps;
					}
				}, keyHolder);
		return keyHolder.getKey()
						.intValue();
	}

	/**
	 * Modifies an existent record in the Employee table
	 *
	 * @param id       an identity of the record to be modified
	 * @param employee a <code>Employee</code> object containing new values
	 * @return a number of the modified records. Normally, it should be equal to one.
	 */
	public int updateEmployee(int id, Employee employee) {
		String query = "UPDATE employee SET first_name=?, last_name=?, department_id=?, job_title=?, gender=CAST(? AS gender_type), date_of_birth=? WHERE id = ?";
		return jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender()
																																					   .toString(), Date.valueOf(employee.getDateOfBirth()), id);
	}

	public void deleteEmployee(int id) {
		String query = "DELETE FROM employee WHERE id = ?";
		jdbcTemplate.update(query, id);
	}
}

class EmployeeRowMapper implements RowMapper<Employee> {
	@Override
	public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
		Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), Gender.valueOf(rs.getString(6)), rs.getDate(7)
																																						   .toLocalDate());
		return employee;
	}
}
