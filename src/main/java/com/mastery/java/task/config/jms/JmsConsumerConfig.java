package com.mastery.java.task.config.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
@RequiredArgsConstructor
public class JmsConsumerConfig {

  private final ConnectionFactory connectionFactory;

  @Autowired
  @Bean(name = "queueListenerFactory")
  public JmsListenerContainerFactory jmsListenerQueueContainerFactory(
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setSessionTransacted(true);
    return factory;
  }

  @Autowired
  @Bean(name = "topicListenerFactory")
  public JmsListenerContainerFactory jmsListenerTopicContainerFactory(
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setSessionTransacted(true);
    factory.setPubSubDomain(true);
    return factory;
  }
}
