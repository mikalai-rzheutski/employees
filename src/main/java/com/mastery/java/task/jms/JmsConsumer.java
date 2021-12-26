package com.mastery.java.task.jms;

import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.service.EmployeeService;
import com.mastery.java.task.service.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class JmsConsumer {

  private final EmployeeService employeeService;

  private final JmsTemplate topicJmsTemplate;

  @Value("${jms.update.topic}")
  private String jmsUpdateTopic;

  @Transactional
  @JmsListener(destination = "${jms.update.queue}", containerFactory = "queueListenerFactory")
  public void updateEmployee(Employee employee) {
    topicJmsTemplate.convertAndSend(jmsUpdateTopic, employee);
    try {
      employeeService.updateEmployee(employee);
    } catch (EntityNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  @JmsListener(destination = "${jms.update.topic}", containerFactory = "topicListenerFactory")
  public void doSomethingWhenUpdatingEmployee(Employee employee) {
    log.info("A message for updating of Employee with id = {} was sent", () -> employee.getId());
  }
}
