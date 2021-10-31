package com.mastery.java.task.repository;

import com.mastery.java.task.logger.annotations.LogMethodCall;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.entities.employee.Employee;
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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeDao implements EmployeeRepository {

	public static final String GET_EMPLOYEES = "SELECT * FROM employee ORDER BY id ASC";

    public static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id = :id";

    public static final String INSERT_EMPLOYEE = "INSERT INTO employee(" +
			"first_name, last_name, department_id, job_title, gender, date_of_birth)" +
			" VALUES (:firstName, :lastName, :departmentId, :jobTitle, CAST(:gender AS gender_type), :dateOfBirth)";

    public static final String UPDATE_EMPLOYEE =
            "UPDATE employee SET first_name = :firstName," + " last_name = :lastName," +
            " department_id = :departmentId," + " job_title = :jobTitle," + " gender = CAST(:gender AS gender_type)," +
            " date_of_birth = :dateOfBirth" + " WHERE id = :id";

    public static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM employee WHERE id = :id";

    public static final String COUNT_EMPLOYEES_BY_ID = "SELECT COUNT(1) FROM employee WHERE id = :id";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Employee> findAll() {
		return namedParameterJdbcTemplate.query(GET_EMPLOYEES, new EmployeeRowMapper());
	}

    public Optional<Employee> findById(Integer id) {
		Employee employee;
		try {
			employee = namedParameterJdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID, new MapSqlParameterSource()
					.addValue("id", id), new EmployeeRowMapper());
		} catch (EmptyResultDataAccessException e) {
            employee = null;
        }
        return Optional.ofNullable(employee);
    }

    @LogMethodCall
    public Employee save(Employee employee) {
        if ( employee.getId() == null ) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate
                    .update(INSERT_EMPLOYEE, new EmployeePropertySqlParameterSource(employee), keyHolder,
                            new String[]{"id"});
            employee.setId(keyHolder.getKey().intValue());
        }
        else {
            namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE, new EmployeePropertySqlParameterSource(employee));
        }
        return employee;
    }

    public void deleteById(Integer id) {
		namedParameterJdbcTemplate.update(DELETE_EMPLOYEE_BY_ID, new MapSqlParameterSource()
				.addValue("id", id));
	}

    public boolean existsById(Integer id) {
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