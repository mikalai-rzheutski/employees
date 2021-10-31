package com.mastery.java.task.service;

import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private static final String NOT_FOUND_MSG =
            "Employee with id=%d cannot be %s since it was not found in the database.";

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        employees.sort(Comparator.comparing(Employee::getId));
        return employees;
    }

    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(String.format(NOT_FOUND_MSG, id, "read")));
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(int id,
                                   Employee employee) {
        throwExceptionIfNotFound(id, "updated");
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(int id) {
        throwExceptionIfNotFound(id, "deleted");
        employeeRepository.deleteById(id);
    }

    private void throwExceptionIfNotFound(int id,
                                          String action) {
        if ( !employeeRepository.existsById(id) ) {
            throw new EmployeeNotFoundException(String.format(NOT_FOUND_MSG, id, action));
        }
    }

}
