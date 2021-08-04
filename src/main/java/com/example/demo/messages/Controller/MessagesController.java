package com.example.demo.messages.Controller;


import com.example.demo.messages.Service.SendService;
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

  @PostMapping("/send")
  public HttpEntity sendMessage(@RequestParam(value = "message", defaultValue = "no message", required = false) String message) throws IOException, TimeoutException {
    sendService.sendMessage(message);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World", required = false) String name) {
    return String.format("Controller operando", name);
  }
}
