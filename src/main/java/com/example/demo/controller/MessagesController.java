package com.example.demo.controller;


import com.example.demo.producers.SendServiceExchanges;
import com.example.demo.producers.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
public class MessagesController {

  @Autowired
  SendService sendService;

  @Autowired
  SendServiceExchanges sendServiceExchanges;

  @PostMapping("/send")
  public HttpEntity sendMessage(@RequestParam(value = "message", defaultValue = "no message", required = false) String message) throws IOException, TimeoutException {
    sendService.sendMessage(message);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/send/fanout")
  public HttpEntity sendMessageFanout(@RequestParam(value = "message", defaultValue = "send to all consumers", required = false) String message) throws IOException, TimeoutException {
    sendServiceExchanges.sendMessageFanout(message);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World", required = false) String name) {
    return String.format("Controller operando", name);
  }
}
