package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

  @Autowired
  private Queue firstQueue;

  @Autowired
  private Queue secondQueue;

  @Bean
  public Exchange fanoutExchange() {
    return ExchangeBuilder
        .fanoutExchange("FANOUT-EXCHANGE-BASIC")
        .durable(true)
        .build();
  }

  @Bean
  public Binding firstFanoutBinding() {
    return BindingBuilder
        .bind(firstQueue)
        .to(fanoutExchange())
        .with("")
        .noargs();
  }

  @Bean
  public Binding secondFanoutBinding() {
    return BindingBuilder
        .bind(secondQueue)
        .to(fanoutExchange())
        .with("")
        .noargs();
  }
}
