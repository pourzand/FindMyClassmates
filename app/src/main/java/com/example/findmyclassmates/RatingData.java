package com.example.findmyclassmates;

public class RatingData {
    private String username;
    private String ratingText;
    private String className;
    private int upvotes;
    private boolean isUpvotedByCurrentUser;
    private int downvotes; // New field for downvotes
    private boolean isDownvotedByCurrentUser; // New field to track if current user downvoted

    public RatingData(String username, String ratingText, int upvotes, int downvotes, String className) {
        this.username = username;
        this.ratingText = ratingText;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.className = className;
        this.isUpvotedByCurrentUser = false;
        this.isDownvotedByCurrentUser = false;
    }


    // Add getter and setter for upvotes
    public int getUpvotes() {
        return upvotes;
    }

    public String getClassName() {
        return className;
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

    public String getRatingText() {
        return ratingText;
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
