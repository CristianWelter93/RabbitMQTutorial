package com.example.demo.controller;


import com.example.demo.entity.MessageText;
import com.example.demo.producers.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
public class MessagesController {

  @Autowired
  SendService sendService;

  @PostMapping("/send")
  public HttpEntity sendMessage(@RequestParam(value = "message", defaultValue = "no message", required = false) String message) throws IOException, TimeoutException {
    sendService.sendMessage(message);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{exchange}/{routingKey}")
  public HttpEntity sendMessageFanout(
      @RequestBody MessageText message,
      @PathVariable String exchange,
      @PathVariable String routingKey
  )  {
    sendService.sendMessageExchanges(exchange, routingKey, message);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World", required = false) String name) {
    return String.format("Controller operando", name);
  }
}
