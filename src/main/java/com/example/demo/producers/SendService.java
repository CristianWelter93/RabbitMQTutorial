package com.example.demo.producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class SendService {

  @Value("${spring.rabitmq.queue}")
  private  String QUEUE_NAME;

  public void sendMessage(String message) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {

      channel.queueDeclare(QUEUE_NAME, true, false, false, null); //b false: persist message

      channel.basicPublish("", QUEUE_NAME,
          MessageProperties.PERSISTENT_TEXT_PLAIN,
          message.getBytes()); // MessageProperties.PERSISTENT_TEXT_PLAIN = persist if rabbit breakdown
      System.out.println(" [x] Sent '" + message + "'");
    }
  }
}
