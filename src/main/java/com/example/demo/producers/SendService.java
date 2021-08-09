package com.example.demo.producers;

import com.example.demo.entity.MessageText;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendService {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private Queue queue;

  public void sendMessage(String message) {
    this.template.convertAndSend(queue.getName(), message);
    System.out.println(" [x] Sent '" + message + "'");
  }

  public void sendMessageExchanges(String exchange, String routingKey, MessageText message) {
    this.template.convertAndSend(exchange, routingKey, message);
    System.out.println(" Sent message '" + message + "' to " +exchange + "  " +  routingKey);
  }
}
