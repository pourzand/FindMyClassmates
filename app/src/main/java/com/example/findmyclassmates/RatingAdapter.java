package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<RatingData> ratings;

    public void setRatings(List<RatingData> ratings) {
        this.ratings = ratings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RatingData rating = ratings.get(position);
        holder.ratingTextView.setText(rating.getRating());
        holder.usernameTextView.setText("User: " + rating.getUsername());
        holder.upvoteCountTextView.setText(String.valueOf(rating.getUpvotes()));
        holder.downvoteCountTextView.setText(String.valueOf(rating.getDownvotes()));

        // Upvote logic
        holder.upvoteIcon.setOnClickListener(v -> handleUpvote(holder, position));

        // Downvote logic
        holder.downvoteIcon.setOnClickListener(v -> handleDownvote(holder, position));
    }

    private void handleUpvote(ViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();
        if (currentPosition != RecyclerView.NO_POSITION) {
            RatingData currentRating = ratings.get(currentPosition);

            // Toggle upvote
            if (currentRating.isUpvotedByCurrentUser()) {
                currentRating.setUpvotes(currentRating.getUpvotes() - 1);
                currentRating.setUpvotedByCurrentUser(false);
            } else {
                currentRating.setUpvotes(currentRating.getUpvotes() + 1);
                currentRating.setUpvotedByCurrentUser(true);
            }
            holder.upvoteCountTextView.setText(String.valueOf(currentRating.getUpvotes()));
            updateRatingInDatabase(currentRating, currentPosition);
        }
    }

    private void handleDownvote(ViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();
        if (currentPosition != RecyclerView.NO_POSITION) {
            RatingData currentRating = ratings.get(currentPosition);

            // Toggle downvote
            if (currentRating.isDownvotedByCurrentUser()) {
                currentRating.setDownvotes(currentRating.getDownvotes() - 1);
                currentRating.setDownvotedByCurrentUser(false);
            } else {
                currentRating.setDownvotes(currentRating.getDownvotes() + 1);
                currentRating.setDownvotedByCurrentUser(true);
            }
            holder.downvoteCountTextView.setText(String.valueOf(currentRating.getDownvotes()));
            updateRatingInDatabase(currentRating, currentPosition);
        }
    }

    private void updateRatingInDatabase(RatingData rating, int position) {
        // Here you'll write logic to update the rating's upvote and downvote count in the database
        // After updating in Firebase, you should refresh the specific item
        // For example: notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return ratings != null ? ratings.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ratingTextView;
        TextView usernameTextView;
        ImageView upvoteIcon;
        TextView upvoteCountTextView;
        ImageView downvoteIcon; // New ImageView for downvote
        TextView downvoteCountTextView; // New TextView for downvote count

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            upvoteIcon = itemView.findViewById(R.id.upvoteIcon);
            upvoteCountTextView = itemView.findViewById(R.id.upvoteCountTextView);
            downvoteIcon = itemView.findViewById(R.id.downvoteIcon); // Initialize the downvote ImageView
            downvoteCountTextView = itemView.findViewById(R.id.downvoteCountTextView); // Initialize the downvote count TextView
        }
    }
}
