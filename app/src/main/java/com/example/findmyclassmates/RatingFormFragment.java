package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingFormFragment extends Fragment {

    private EditText prompt1EditText, prompt2EditText, prompt3EditText, prompt4EditText, prompt5EditText;
    private RatingFormListener mListener;
    private String givenClassID;

    public RatingFormFragment(String classID) {
        givenClassID = classID;
    }

    public void setRatingFormListener(RatingFormListener listener) {
        mListener = listener;
    }

    public interface RatingFormListener {
        void onRatingFormSubmit(String ratingData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rating_form, container, false);

        prompt1EditText = rootView.findViewById(R.id.prompt1EditText);
        prompt2EditText = rootView.findViewById(R.id.prompt2EditText);
        prompt3EditText = rootView.findViewById(R.id.prompt3EditText);
        prompt4EditText = rootView.findViewById(R.id.prompt4EditText);
        prompt5EditText = rootView.findViewById(R.id.prompt5EditText);

        Button submitButton = rootView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating();
            }
        });

        return rootView;
    }

    private void submitRating() {
        // Get the user inputs from the EditText fields
        String response1 = prompt1EditText.getText().toString();
        String response2 = prompt2EditText.getText().toString();
        String response3 = prompt3EditText.getText().toString();
        String response4 = prompt4EditText.getText().toString();
        String response5 = prompt5EditText.getText().toString();

        if (response1.isEmpty() || response2.isEmpty() || response3.isEmpty() || response4.isEmpty()) {
            prompt1EditText.setError("Fields 1-4 cannot be empty");
            return; // Exit the function since a field is blank
        }

        String currentUsername = UserSession.getInstance().getUsername(); // Get username

        // Create the rating text
        String ratingText = "1. The workload of the class: " + response1 + "\n" +
                "2. The score they would like to give to this class: " + response2 + "\n" +
                "3. If the professor checks attendance: " + response3 + "\n" +
                "4. If the professor allows late homework submission: " + response4 + "\n" +
                "5. Other comments: " + response5;

        // Create a RatingData object
        RatingData rating = new RatingData(currentUsername, ratingText, 0, 0, givenClassID);

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("classes")
                .child(givenClassID)
                .child("ratings")
                .child(currentUsername);

        // Set the Rating object to the database
        dbReference.setValue(rating);

        // Clear the EditText fields
        prompt1EditText.setText("");
        prompt2EditText.setText("");
        prompt3EditText.setText("");
        prompt4EditText.setText("");
        prompt5EditText.setText("");

        // Show a success message to the user
        Toast.makeText(getActivity(), "Rating submitted successfully!", Toast.LENGTH_SHORT).show();

        if (mListener != null) {
            mListener.onRatingFormSubmit(ratingText);
        }
    }
}
