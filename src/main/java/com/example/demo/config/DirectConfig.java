package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

  @Autowired
  private Queue firstQueue;

  @Autowired
  private Queue secondQueue;

  @Bean
  public Exchange directExchange() {
    return ExchangeBuilder
        .directExchange("DIRECT-EXCHANGE-BASIC")
        .durable(true)
        .build();
  }

  @Bean
  public Binding firstDirectBinding() {
    return BindingBuilder
        .bind(firstQueue)
        .to(directExchange())
        .with("TO-FIRST-QUEUE")
        .noargs();
  }

  @Bean
  public Binding secondDirectBinding() {
    return BindingBuilder
        .bind(secondQueue)
        .to(directExchange())
        .with("TO-SECOND-QUEUE")
        .noargs();
  }
}
