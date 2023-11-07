package com.example.findmyclassmates;

public class Message {
    private String sender;
    private String receiver;
    private String body;
    private String timestamp;

    // Constructor
    public Message(String sender, String receiver, String body, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
