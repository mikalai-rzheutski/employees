package com.mastery.java.task.repository;

import com.mastery.java.task.model.entities.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();

    Optional<Employee> findById(Integer id);

    Employee save(Employee employee);

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
