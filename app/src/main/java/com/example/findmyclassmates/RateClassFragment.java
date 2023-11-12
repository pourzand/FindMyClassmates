package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RateClassFragment extends Fragment /*implements RatingFormFragment.RatingFormListener*/ {

    private TextView classNameTextView;
    // private TextView responseTextView; // Comment this out if you don't need to display the concatenatedResponses
    // private StringBuilder concatenatedResponses = new StringBuilder(); // Comment out as it's part of the response handling
    private ClassModel selectedClass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_class, container, false);

        classNameTextView = view.findViewById(R.id.classNameTextView);

        if (getArguments() != null) {
            selectedClass = getArguments().getParcelable("selectedClass");
            if (selectedClass != null) {
                classNameTextView.setText("Ratings for : " + selectedClass.getClassName());
            }
        }

        Button buttonAddRating = view.findViewById(R.id.buttonAddRating);
        buttonAddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingFormFragment ratingFormFragment = new RatingFormFragment();
                // Pass any necessary data to ratingFormFragment here before transaction

                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ratingFormFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        // Comment out the TextView responseTextView setup since we're not displaying responses
        // if (responseTextView != null) {
        //     responseTextView.setText(concatenatedResponses.toString());
        // }

        return view;
    }

    // Comment out the onRatingFormSubmit method as it's used for receiving form responses
    /*
    @Override
    public void onRatingFormSubmit(String concatenatedResponses) {
        this.concatenatedResponses.append(concatenatedResponses).append("\n\n");
        if (responseTextView != null) {
            responseTextView.setText(this.concatenatedResponses.toString());
        }
    }
    */
}
