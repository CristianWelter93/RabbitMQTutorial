package com.example.demo.entity;


import java.util.UUID;

public class MessageText {

  private UUID id;

  private String title;

  private String message;

  private String email;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "MessageText{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", message='" + message + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
