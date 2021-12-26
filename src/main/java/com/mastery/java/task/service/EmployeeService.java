package com.mastery.java.task.service;

import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.repository.EmployeeJpaRepository;
import com.mastery.java.task.repository.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private static final String NOT_FOUND_MSG =
      "Employee with id=%d cannot be %s since it was not found in the database.";

  private final EmployeeJpaRepository employeeJpaRepository;

  public List<Employee> getEmployees() {
    return employeeJpaRepository.findAllByOrderByLastName();
  }

  public List<Employee> getEmployees(Map<String, String> searchParams) {
    return employeeJpaRepository.findAll(new EmployeeSpecification(searchParams));
  }

  public Employee getEmployee(int id) {
    return employeeJpaRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MSG, id, "read")));
  }

  public Employee createEmployee(Employee employee) {
    employee.setId(null);
    return employeeJpaRepository.save(employee);
  }

  @Transactional
  public Employee updateEmployee(Employee employee) {
    throwExceptionIfNotFound(employee.getId(), "updated");
    return employeeJpaRepository.save(employee);
  }

  @Transactional
  public void deleteEmployee(int id) {
    throwExceptionIfNotFound(id, "deleted");
    employeeJpaRepository.deleteById(id);
  }

  public void throwExceptionIfNotFound(int id, String action) {
    if (!employeeJpaRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MSG, id, action));
    }
  }
}
