package com.example.findmyclassmates;

public class Message {
    private String sender;
    private String content;
    // Other fields like timestamp can be added here

    // Constructor
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Add setters and getters for other fields as necessary
}
