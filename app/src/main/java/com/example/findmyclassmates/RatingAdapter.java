package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<RatingData> ratings; // Change the type if needed

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
    }

    @Override
    public int getItemCount() {
        return ratings != null ? ratings.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ratingTextView;
        TextView usernameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }
}
