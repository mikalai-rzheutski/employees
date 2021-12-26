package com.mastery.java.task.repository;

import com.mastery.java.task.model.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeJpaRepository
    extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

  List<Employee> findAllByOrderByLastName();
}
