package com.example.findmyclassmates;

public class RatingData {
    private String username;
    private String rating;
    private int upvotes;
    private boolean isUpvotedByCurrentUser;
    private int downvotes; // New field for downvotes
    private boolean isDownvotedByCurrentUser; // New field to track if current user downvoted

    public RatingData(String username, String rating) {
        this.username = username;
        this.rating = rating;
        this.upvotes = 0;
        this.downvotes = 0;
    }

    // Add getter and setter for upvotes
    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    // Existing getters
    public String getUsername() {
        return username;
    }

    public String getRating() {
        return rating;
    }

    public boolean isUpvotedByCurrentUser() {
        return isUpvotedByCurrentUser;
    }

    public boolean isDownvotedByCurrentUser() {
        return isDownvotedByCurrentUser;
    }

    public void setUpvotedByCurrentUser(boolean upvotedByCurrentUser) {
        isUpvotedByCurrentUser = upvotedByCurrentUser;
    }

    public void setDownvotedByCurrentUser(boolean downvotedByCurrentUser) {
        isDownvotedByCurrentUser = downvotedByCurrentUser;
    }
}
