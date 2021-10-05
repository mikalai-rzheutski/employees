package com.mastery.java.task.service;

import com.mastery.java.task.dal.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.rest.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcEmployeeService extends EmployeeService {

	private static final Logger log4jLog = LogManager.getLogger(JdbcEmployeeService.class);

	private final EmployeeDao employeeDao;

	public JdbcEmployeeService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
		log4jLog.info("JdbcEmployeeService is created");
	}

	@Override
	public List<Employee> getAllEmployees() {
		log();
		return employeeDao.getAll();
	}

	@Override
	public Employee getEmployee(int id) {
		log();
		Employee employee = employeeDao.read(id);
		if (employee == null) throw new NotFoundException("Employee with id=" + id + " was not found in the database.");
		return employee;
	}

	@Override
	public int createEmployee(Employee employee) {
		log();
		return employeeDao.create(employee);
	}

	@Override
	@Transactional
	public void updateEmployee(int id, Employee employee) {
		log();
		if (!employeeDao.isEmployeeExistent(id))
			throw new NotFoundException("Employee with id=" + id + " cannot be updated since it was not found in the database.");
		employeeDao.update(id, employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(int id) {
		log();
		if (!employeeDao.isEmployeeExistent(id))
			throw new NotFoundException("Employee with id=" + id + " was not deleted since it was not found in the database.");
		employeeDao.delete(id);
	}

	@Override
	protected Logger getLogger() {
		return log4jLog;
	}
}
