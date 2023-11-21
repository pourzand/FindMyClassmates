package com.example.findmyclassmates;
public class RatingData {
    private String username;
    private String rating;

    public RatingData(String username, String rating) {
        this.username = username;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public String getRating() {
        return rating;
    }
}

