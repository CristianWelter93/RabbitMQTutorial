package com.example.demo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${spring.rabitmq.queue}")
  private String queue;

  @Bean
  public Queue queue() {
    return new Queue(queue,true);
  }

  /*  UTILIZAR quando estiver enviando um objeto no formato JSON
  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  */

}
