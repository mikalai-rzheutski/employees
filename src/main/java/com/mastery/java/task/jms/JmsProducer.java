package com.mastery.java.task.jms;

import com.mastery.java.task.model.entities.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JmsProducer {

  private final JmsTemplate queueJmsTemplate;

  @Value("${jms.update.queue}")
  private String jmsUpdateQueue;

  @Transactional
  public void updateEmployee(Employee employee) {
    queueJmsTemplate.convertAndSend(jmsUpdateQueue, employee);
  }
}
