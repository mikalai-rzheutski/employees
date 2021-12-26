package com.mastery.java.task.config.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@RequiredArgsConstructor
public class JmsProducerConfig {

  private final ConnectionFactory connectionFactory;

  @Bean(name = "topicJmsTemplate")
  public JmsTemplate jmsTopicTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    template.setSessionTransacted(true);
    template.setPubSubDomain(true);
    return template;
  }

  @Bean(name = "queueJmsTemplate")
  public JmsTemplate jmsQueueTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    template.setSessionTransacted(true);
    return template;
  }
}
