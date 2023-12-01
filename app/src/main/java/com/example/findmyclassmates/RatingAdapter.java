package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        holder.ratingTextView.setText(rating.getRatingText());
        holder.usernameTextView.setText("User: " + rating.getUsername());
        holder.upvoteCountTextView.setText(String.valueOf(rating.getUpvotes()));
        holder.downvoteCountTextView.setText(String.valueOf(rating.getDownvotes()));

        // Upvote and Downvote logic
        holder.upvoteIcon.setOnClickListener(v -> handleUpvote(holder, position));
        holder.downvoteIcon.setOnClickListener(v -> handleDownvote(holder, position));
    }

    private void handleUpvote(ViewHolder holder, int position) {
        RatingData currentRating = ratings.get(position);

        // If already upvoted, undo the upvote
        if (currentRating.isUpvotedByCurrentUser()) {
            currentRating.setUpvotes(currentRating.getUpvotes() - 1);
            currentRating.setUpvotedByCurrentUser(false);
        } else {
            // If not upvoted, increase the upvote count
            currentRating.setUpvotes(currentRating.getUpvotes() + 1);
            currentRating.setUpvotedByCurrentUser(true);

            // If previously downvoted, remove the downvote
            if (currentRating.isDownvotedByCurrentUser()) {
                currentRating.setDownvotes(currentRating.getDownvotes() - 1);
                currentRating.setDownvotedByCurrentUser(false);
            }
        }

        // Update the UI
        holder.upvoteCountTextView.setText(String.valueOf(currentRating.getUpvotes()));
        holder.downvoteCountTextView.setText(String.valueOf(currentRating.getDownvotes()));

        // Update the database
        updateRatingInDatabase(currentRating, position);
    }

    private void handleDownvote(ViewHolder holder, int position) {
        RatingData currentRating = ratings.get(position);

        // If already downvoted, undo the downvote
        if (currentRating.isDownvotedByCurrentUser()) {
            currentRating.setDownvotes(currentRating.getDownvotes() - 1);
            currentRating.setDownvotedByCurrentUser(false);
        } else {
            // If not downvoted, increase the downvote count
            currentRating.setDownvotes(currentRating.getDownvotes() + 1);
            currentRating.setDownvotedByCurrentUser(true);

            // If previously upvoted, remove the upvote
            if (currentRating.isUpvotedByCurrentUser()) {
                currentRating.setUpvotes(currentRating.getUpvotes() - 1);
                currentRating.setUpvotedByCurrentUser(false);
            }
        }

        // Update the UI
        holder.upvoteCountTextView.setText(String.valueOf(currentRating.getUpvotes()));
        holder.downvoteCountTextView.setText(String.valueOf(currentRating.getDownvotes()));

        // Update the database
        updateRatingInDatabase(currentRating, position);
    }

    private void updateRatingInDatabase(RatingData rating, int position) {
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("classes")
                .child(rating.getClassName())
                .child("ratings")
                .child(rating.getUsername());

        // Update upvotes and downvotes in the database
        dbReference.child("upvotes").setValue(rating.getUpvotes());
        dbReference.child("downvotes").setValue(rating.getDownvotes());

        // Notify the item changed to refresh the UI
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return ratings != null ? ratings.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ratingTextView, usernameTextView, upvoteCountTextView, downvoteCountTextView;
        ImageView upvoteIcon, downvoteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            upvoteIcon = itemView.findViewById(R.id.upvoteIcon);
            upvoteCountTextView = itemView.findViewById(R.id.upvoteCountTextView);
            downvoteIcon = itemView.findViewById(R.id.downvoteIcon);
            downvoteCountTextView = itemView.findViewById(R.id.downvoteCountTextView);
        }
    }
}
