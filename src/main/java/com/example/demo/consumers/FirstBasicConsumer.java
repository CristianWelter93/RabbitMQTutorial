package com.example.demo.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class FirstBasicConsumer {

  @RabbitListener(queues = "${first.queue.basic}")
  public void receiveMessageFromFirstQueue(Message message) throws UnsupportedEncodingException {
    System.out.println("Receive message from -> " + message.getMessageProperties().getConsumerQueue());
    String bodyAsString =  new String(message.getBody(), "UTF-8");;
    System.out.println("Message: " + bodyAsString + "\n");
  }

  @RabbitListener(queues = "${second.queue.basic}")
  public void receiveMessageFromSecondQueue(Message message) throws UnsupportedEncodingException {
    System.out.println("Receive message from -> " + message.getMessageProperties().getConsumerQueue());
    String bodyAsString =  new String(message.getBody(), "UTF-8");;
    System.out.println("Message: " + bodyAsString + "\n");
  }
}
