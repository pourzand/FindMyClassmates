package com.example.findmyclassmates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RateClassFragment extends Fragment {

    private TextView classNameTextView;
    private ImageView backButton;
    private Button buttonAddRating;
    private RecyclerView recyclerView;
    private RatingAdapter ratingAdapter;
    private ClassModel selectedClass;
    private DatabaseReference ratingsReference;
    private List<RatingData> ratingsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_class, container, false);

        classNameTextView = view.findViewById(R.id.classNameTextView);
        backButton = view.findViewById(R.id.backButton);
        buttonAddRating = view.findViewById(R.id.buttonAddRating);
        recyclerView = view.findViewById(R.id.ratingsRecyclerView);

        if (getArguments() != null) {
            selectedClass = getArguments().getParcelable("selectedClass");
            classNameTextView.setText("Ratings for: " + selectedClass.getClassName());
            ratingsReference = FirebaseDatabase.getInstance().getReference("classes")
                    .child(selectedClass.getClassID())
                    .child("ratings");
            retrieveAndDisplayRatings();
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                // You may want to pop the fragment from the stack or perform other actions
            }
        });

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


        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ratingAdapter = new RatingAdapter();
        recyclerView.setAdapter(ratingAdapter);

        return view;
    }

    private void updateConcatenatedResponses(String ratingData) {

        // Update the responseTextView
//        TextView responseTextView = getView().findViewById(R.id.responseTextView);
//        responseTextView.setText(ratingData);
    }

    private void retrieveAndDisplayRatings() {
        ratingsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ratingsList.clear();
                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    String ratingUsername = ratingSnapshot.getKey(); // Get the username
                    String ratingData = ratingSnapshot.getValue(String.class);
                    Log.println(Log.DEBUG,"ratingUsername", "ratingUsername: " + ratingUsername);

                    ratingsList.add(new RatingData(ratingUsername, ratingData,selectedClass.getClassID()));
                }
                ratingAdapter.setRatings(ratingsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RateClassFragment", "Error retrieving ratings: " + databaseError.getMessage());
            }
        });
    }
}
