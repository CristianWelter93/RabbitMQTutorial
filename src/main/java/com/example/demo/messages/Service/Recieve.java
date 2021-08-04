package com.example.demo.messages.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Recieve {
  private final static String QUEUE_NAME = "hello";

  @PostConstruct
  public void readMessageFirst() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    System.out.println(" [*] Waiting for messages");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [x] Received by First worker'" + message + "'");

      try {
        try {
          doWork(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } finally {
        System.out.println(" [x] Done");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
  }

  @PostConstruct
  public void readMessageSecond() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    System.out.println(" [*] Waiting for messages");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [x] Received by Second worker'" + message + "'");

      try {
        try {
          doWork(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } finally {
        System.out.println(" [x] Done");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
  }


  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') Thread.sleep(1000);
    }
  }
}
