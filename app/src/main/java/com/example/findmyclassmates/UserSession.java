package com.example.findmyclassmates;

public class UserSession {
    private static UserSession instance;
    private String username;
    // Other user information as needed

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Other getters and setters as needed
}
