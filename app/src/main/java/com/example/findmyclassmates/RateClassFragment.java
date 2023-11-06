package com.example.findmyclassmates;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class RateClassFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_class, container, false);

        Button buttonAddRating = view.findViewById(R.id.buttonAddRating);
        buttonAddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming you have a RatingFormFragment
                RatingFormFragment ratingFormFragment = new RatingFormFragment();
                // Optionally pass any arguments to the RatingFormFragment

                // Replace the current fragment with RatingFormFragment
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ratingFormFragment) // Replace 'fragment_container' with your actual container ID
                            .addToBackStack(null) // Add transaction to back stack to enable back navigation
                            .commit();
                }
            }
        });

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle what happens when the back button is clicked
                // Typically, we just pop the back stack
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }
}
