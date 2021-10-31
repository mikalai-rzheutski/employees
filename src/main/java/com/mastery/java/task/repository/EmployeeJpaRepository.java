package com.mastery.java.task.repository;

import com.mastery.java.task.model.entities.employee.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.Repository;

@Primary
public interface EmployeeJpaRepository extends EmployeeRepository, Repository<Employee, Integer> {
}