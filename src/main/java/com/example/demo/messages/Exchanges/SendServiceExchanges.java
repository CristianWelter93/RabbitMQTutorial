package com.example.demo.messages.Exchanges;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class SendServiceExchanges {
  private static final String EXCHANGE_NAME_FANOUT = "message_fanout";

  public void sendMessageFanout(String message) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {

      channel.exchangeDeclare(EXCHANGE_NAME_FANOUT, "fanout");

      channel.basicPublish(EXCHANGE_NAME_FANOUT, "", null, message.getBytes("UTF-8"));

      System.out.println(" [x] Sent '" + message + "'");
    }
  }
}
