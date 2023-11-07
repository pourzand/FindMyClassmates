package com.example.findmyclassmates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RateClassFragment extends Fragment {

    private TextView classNameTextView;
    private StringBuilder concatenatedResponses = new StringBuilder();
    private ClassModel selectedClass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_class, container, false);

        classNameTextView = view.findViewById(R.id.classNameTextView);

        if (getArguments() != null) {
            selectedClass = getArguments().getParcelable("selectedClass");
            classNameTextView.setText("Ratings for: " + selectedClass.getClassName());
        }

        Button buttonAddRating = view.findViewById(R.id.buttonAddRating);
        buttonAddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.DEBUG,"ratingClassFrag", "test0: " + selectedClass.getClassID());

                // Create a RatingFormFragment and pass the classID
                RatingFormFragment ratingFormFragment = new RatingFormFragment(selectedClass.getClassID());

                // Set a listener to handle the form submission callback
                ratingFormFragment.setRatingFormListener(new RatingFormFragment.RatingFormListener() {
                    @Override
                    public void onRatingFormSubmit(String ratingData) {
                        // Handle the submitted rating data here
                        updateConcatenatedResponses(ratingData);
                    }
                });

                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ratingFormFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        TextView responseTextView = view.findViewById(R.id.responseTextView);
        responseTextView.setText(concatenatedResponses.toString());

        return view;
    }

    private void updateConcatenatedResponses(String ratingData) {

        // Update the responseTextView
//        TextView responseTextView = getView().findViewById(R.id.responseTextView);
//        responseTextView.setText(ratingData);
    }
}
