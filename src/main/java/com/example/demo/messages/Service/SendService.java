package com.example.demo.messages.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class SendService {
  private final static String QUEUE_NAME = "hello";

  public void sendMessage(String message) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();

    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {

      channel.queueDeclare(QUEUE_NAME, false, false, false, null);

      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
      System.out.println(" [x] Sent '" + message + "'");
    }
  }
}
