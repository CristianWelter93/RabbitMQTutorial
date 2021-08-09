package com.example.demo.consumers;

import com.example.demo.entity.MessageText;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class FirstBasicConsumer {

  @RabbitListener(queues = "${first.queue.basic}")
  public void receiveMessageFromFirstQueue(@Payload MessageText message)  {
    System.out.println("Receive Message FIRST QUEUE: " + message.getMessage() + "\n");
  }

  @RabbitListener(queues = "${second.queue.basic}")
  public void receiveMessageFromSecondQueue(@Payload MessageText message)  {
    System.out.println("Receive Message SECOND QUEUE: " + message.getMessage() + "\n");
  }
}
