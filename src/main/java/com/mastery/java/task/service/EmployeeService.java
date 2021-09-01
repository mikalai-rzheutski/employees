package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.rest.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeDao employeeDao;

	/**
	 * Returns all the Employees from the database
	 *
	 * @return a List of employees
	 */
	public List<Employee> fetchAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	/**
	 * Returns an Employee by its id
	 *
	 * @param id identifier of the Employee to be returned
	 * @return an Employee object
	 */
	public Employee getEmployee(int id) {
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee == null) throw new NotFoundException("Employee with id=" + id + " was not found in the database.");
		return employee;
	}

	/**
	 * Saves a new Employee to the database.
	 * @param employee Employee object to be saved
	 * @return id of the created object
	 */
	public int createEmployee(Employee employee) {
		return employeeDao.addEmployee(employee);
	}

	/**
	 * Updates the existent Employee in the database
	 * @param id identifier of the Employee to be updated
	 * @param employee the new Employee object
	 */
	@Transactional
	public void updateEmployee(int id, Employee employee) {
		if (!employeeDao.isEmployeeExistent(id))
			throw new NotFoundException("Employee with id=" + id + " cannot be updated since it was not found in the database.");
		employeeDao.updateEmployee(id, employee);
	}

	/**
	 * Deletes an Employee from the database
	 * @param id identifier of the Employee to be deleted
	 */
	@Transactional
	public void deleteEmployee(int id) {
		if (!employeeDao.isEmployeeExistent(id))
			throw new NotFoundException("Employee with id=" + id + " was not deleted since it was not found in the database.");
		employeeDao.deleteEmployee(id);
	}
}
