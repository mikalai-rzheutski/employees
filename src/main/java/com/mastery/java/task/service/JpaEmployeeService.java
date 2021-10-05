package com.mastery.java.task.service;

import com.mastery.java.task.dal.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.rest.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Primary
public class JpaEmployeeService extends EmployeeService {

    private static final org.apache.logging.log4j.Logger log4jLog = LogManager.getLogger(JpaEmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public JpaEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        log4jLog.info("JpaEmployeeService is created");
    }

    @Override
    public List<Employee> getAllEmployees() {
        log();
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Employee getEmployee(int id) {
        log();
        Employee employee;
        try {
            employee = employeeRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Employee with id=" + id + " was not found in the database.");
        }
        return employee;
    }

    @Override
    public int createEmployee(Employee employee) {
        log();
        employee.setId(null);
        return employeeRepository.save(employee).getId();
    }

    @Override
    @Transactional
    public void updateEmployee(int id, Employee employee) {
        log();
        if (!employeeRepository.existsById(id))
            throw new NotFoundException("Employee with id=" + id + " cannot be updated since it was not found in the database.");
        employee.setId(id);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        log();
        if (!employeeRepository.existsById(id))
            throw new NotFoundException("Employee with id=" + id + " was not deleted since it was not found in the database.");
        employeeRepository.deleteById(id);
    }

    @Override
    protected Logger getLogger() {
        return log4jLog;
    }

}
