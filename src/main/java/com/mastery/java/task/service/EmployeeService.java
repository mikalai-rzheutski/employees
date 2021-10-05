package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class EmployeeService {

    public abstract List<Employee> getAllEmployees();

    public abstract Employee getEmployee(int id);

    public abstract int createEmployee(Employee employee);

    public abstract void updateEmployee(int id, Employee employee);

    public abstract void deleteEmployee(int id);

    protected abstract Logger getLogger();

    protected void log() {
        getLogger().info("Method {} was called from {}",
                () -> Thread.currentThread().getStackTrace()[2].getMethodName(),
                () -> this.getClass().getName());
    }
}
