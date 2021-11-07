package com.mastery.java.task.jms;

import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.service.EmployeeNotFoundException;
import com.mastery.java.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class JmsConsumer {

    private final EmployeeService employeeService;

    @JmsListener(destination = "${jms.update.topic}")
    public void updateEmployee(Employee employee) {
        try {
            employeeService.updateEmployee(employee);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "${jms.update.topic}")
    public void doSomethingWhenUpdatingEmployee(Employee employee) {
        log.info("A message for updating of Employee with id = {} was sent", () -> employee.getId());
    }
}
