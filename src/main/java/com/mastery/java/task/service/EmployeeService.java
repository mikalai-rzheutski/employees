package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.rest.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
	@Autowired
	EmployeeDao employeeDao;

	@Transactional
	public List<Employee> fetchAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Transactional
	public Employee getEmployee(int id) throws NotFoundException {
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee == null) throw new NotFoundException("Employee with id=" + id + " was not found in the database.");
		return employee;
	}

	@Transactional
	public int createEmployee(Employee employee) {
		return employeeDao.addEmployee(employee);
	}

	@Transactional
	public void updateEmployee(int id, Employee employee) {
		if (employeeDao.getEmployeeById(id) == null)
			throw new NotFoundException("Employee with id=" + id + " cannot be updated since it was not found in the database.");
		employeeDao.updateEmployee(id, employee);
	}

	@Transactional
	public void deleteEmployee(int id) {
		if (employeeDao.getEmployeeById(id) == null)
			throw new NotFoundException("Employee with id=" + id + " was not deleted since it was not found in the database.");
		employeeDao.deleteEmployee(id);
	}
}
