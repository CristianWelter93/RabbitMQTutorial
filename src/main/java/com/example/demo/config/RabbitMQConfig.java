package com.example.demo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${spring.rabitmq.queue}")
  private String queue;

  @Value("${first.queue.basic}")
  private String firstQueue;

  @Value("${second.queue.basic}")
  private String secondQueue;

  @Value("${json.queue.basic}")
  private String jsonQueue;

  @Bean
  public Queue queue() {
    return QueueBuilder
        .durable(queue)
        .build();
  }

  @Bean
  public Queue firstQueue() {
    return QueueBuilder
        .durable(firstQueue)
        .build();
  }

  @Bean
  public Queue secondQueue() {
    return QueueBuilder
        .durable(secondQueue)
        .build();
  }

  @Bean
  public Queue jsonQueue() {
    return QueueBuilder
        .durable(jsonQueue)
        .build();
  }

  /*  UTILIZAR quando estiver enviando um objeto no formato JSON
  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  */

}
