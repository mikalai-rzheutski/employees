package com.mastery.java.task.jms;

import com.mastery.java.task.model.entities.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JmsProducer {

    private final JmsTemplate jmsTemplate;

    @Value("${jms.update.topic}")
    private String jmsUpdateTopic;

    public void updateEmployee(Employee employee) {
        jmsTemplate.convertAndSend(jmsUpdateTopic, employee);
    }
}
