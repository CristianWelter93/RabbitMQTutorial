package com.example.demo.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

  @RabbitListener(queues = "${spring.rabitmq.queue}")
  public void listen(@Payload String message) throws Exception {

    System.out.println(" QueueConsumer :: Received '" + message + "'");

    try {
      doWork(message);
    } finally {
      System.out.println(" Work Done");
    }
  }

  private static void doWork(String task) {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
